package Juego_original;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;

public final class TextConsoleWindow {

    private static TextConsoleWindow INSTANCE;

    private final JFrame frame;
    private JTextArea textArea = null;
    private final JTextField inputField;
    private final JSlider delaySlider;
    private final JCheckBox autoScrollCheck;

    private volatile int artificialDelayMs = 150;

    private final PipedInputStream pipedIn;
    private final PipedOutputStream pipedInSrc;

    private TextConsoleWindow(String title, boolean lineWrap) throws IOException {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationByPlatform(true);

        JPanel header = getJPanel();

        JLabel titleLbl = new JLabel("Consola de juego");
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setFont(titleLbl.getFont().deriveFont(Font.BOLD, 16f));
        header.add(titleLbl, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        headerRight.setOpaque(false);

        delaySlider = new JSlider(0, 800, artificialDelayMs);
        delaySlider.setToolTipText("Velocidad de mensajes (ms por linea)");
        delaySlider.addChangeListener(e -> artificialDelayMs = delaySlider.getValue());
        headerRight.add(new JLabel("<html><span style='color:#DDD'>Velocidad</span></html>"));
        headerRight.add(delaySlider);

        autoScrollCheck = new JCheckBox("Auto-scroll", true);
        autoScrollCheck.setOpaque(false);
        autoScrollCheck.setForeground(Color.WHITE);
        autoScrollCheck.addActionListener(ae -> {
            DefaultCaret caret = (DefaultCaret) textArea.getCaret();
            caret.setUpdatePolicy(autoScrollCheck.isSelected()
                    ? DefaultCaret.ALWAYS_UPDATE
                    : DefaultCaret.NEVER_UPDATE);
        });
        headerRight.add(autoScrollCheck);

        JButton dividerBtn = new JButton("Separador");
        dividerBtn.addActionListener(ae -> append("\n────────────────────────────────────────────────────────\n"));
        headerRight.add(dividerBtn);

        header.add(headerRight, BorderLayout.EAST);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        textArea.setLineWrap(lineWrap);
        textArea.setWrapStyleWord(lineWrap);

        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        inputField = new JTextField();
        inputField.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        inputField.setToolTipText("Escribe aquí y pulsa Enter para enviarlo al programa");

        pipedIn = new PipedInputStream(8192);
        pipedInSrc = new PipedOutputStream(pipedIn);

        inputField.addActionListener((ActionEvent e) -> {
            String text = inputField.getText();
            if (text == null) {
                text = "";
            }
            try {
                append("> " + text + System.lineSeparator());
                byte[] bytes = (text + System.lineSeparator()).getBytes(StandardCharsets.UTF_8);
                pipedInSrc.write(bytes);
                pipedInSrc.flush();
            } catch (IOException ex) {
                appendErr("[ERROR] No se ha podido escribir a la consola de entrada: " + ex.getMessage() + System.lineSeparator());
            } finally {
                inputField.setText("");
            }
        });

        JPanel bottom = new JPanel(new BorderLayout(6, 0));
        bottom.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        bottom.add(inputField, BorderLayout.CENTER);

        JButton clearBtn = new JButton("Limpia");
        clearBtn.addActionListener(ae -> textArea.setText(""));
        bottom.add(clearBtn, BorderLayout.EAST);

        frame.setLayout(new BorderLayout());
        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
    }

    private static JPanel getJPanel() {
        JPanel header = new JPanel(new BorderLayout(8, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int w = getWidth();
                int h = getHeight();
                Color c1 = new Color(30, 30, 30);
                Color c2 = new Color(55, 55, 55);
                g2.setPaint(new GradientPaint(0, 0, c1, 0, h, c2));
                g2.fillRect(0, 0, w, h);
                g2.dispose();
            }
        };
        header.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return header;
    }

    private void show() {
        frame.setVisible(true);
        inputField.requestFocusInWindow();
    }

    private void append(String s) {
        if (SwingUtilities.isEventDispatchThread()) {
            textArea.append(s);
        } else {
            SwingUtilities.invokeLater(() -> textArea.append(s));
        }
    }

    private void appendErr(String s) {
        append("[ERROR] " + s);
    }

    private OutputStream createOutputStream(boolean isErr) {
        return new OutputStream() {
            private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            @Override
            public void write(int b) {
                buffer.write(b);
                if (b == '\n') {
                    flush();
                }
            }

            @Override
            public void write(byte[] b, int off, int len) {
                buffer.write(b, off, len);
                for (int i = off; i < off + len; i++) {
                    if (b[i] == '\n') {
                        flush();
                        break;
                    }
                }
            }

            @Override
            public void flush() {
                String s = buffer.toString(StandardCharsets.UTF_8);
                buffer.reset();
                if (!s.isEmpty()) {
                    if (!isErr) {
                        int delay = artificialDelayMs;
                        if (delay > 0) {
                            try {
                                Thread.sleep(delay);
                            } catch (InterruptedException ignored) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                    if (isErr) {
                        appendErr(s);
                    } else {
                        append(s);
                    }
                }
            }

            @Override
            public void close() {
                flush();
            }
        };
    }

    public static synchronized void install(String title, boolean lineWrap) {
        if (INSTANCE != null) return;

        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    INSTANCE = new TextConsoleWindow(title, lineWrap);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            OutputStream outStream = INSTANCE.createOutputStream(false);
            OutputStream errStream = INSTANCE.createOutputStream(true);

            PrintStream outPs = new PrintStream(outStream, true, StandardCharsets.UTF_8);
            PrintStream errPs = new PrintStream(errStream, true, StandardCharsets.UTF_8);

            System.setOut(outPs);
            System.setErr(errPs);

            System.setIn(INSTANCE.pipedIn);

            SwingUtilities.invokeLater(INSTANCE::show);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setLineDelayMs(int ms) {
        if (INSTANCE != null) {
            INSTANCE.artificialDelayMs = Math.max(0, ms);
            if (INSTANCE.delaySlider != null) {
                SwingUtilities.invokeLater(() -> INSTANCE.delaySlider.setValue(INSTANCE.artificialDelayMs));
            }
        }
    }

    public static void printDivider() {
        if (INSTANCE != null) {
            INSTANCE.append("\n────────────────────────────────────────────────────────\n");
        }
    }

    public static void printBanner(String text) {
        if (INSTANCE != null) {
            String t = " " + (text == null ? "" : text.trim()) + " ";
            String line = "═".repeat(Math.max(8, t.length() + 4));
            INSTANCE.append("\n" + line + "\n" + "╡" + t + "╞\n" + line + "\n");
        }
    }
}
