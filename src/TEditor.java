import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;


public class TEditor extends JFrame {
    private JPanel panel1;
    private JTextArea textArea1;
    private JScrollPane pane1;
    private JToolBar leiste;
    private JButton neuBT;
    private JButton oeffnenBT;
    private JButton speicherBT;

    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Textdateien (*.txt", "txt");




    class MeinListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //welcher Eintrag ist angeklickt worden?
            if (e.getActionCommand().equals("neu"))
                neu();
            if (e.getActionCommand().equals("oeffnen"))
                oeffnen();
            if (e.getActionCommand().equals("speichern"))
                speichern();
            if (e.getActionCommand().equals("beenden"))
                beenden();
        }
    }

    public TEditor(String titel) {
        super(titel);

        setContentPane(panel1);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        symbolleiste();
        menu();
        setVisible(true);
        setSize(640, 480);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private void menu() {

        JMenuBar menue = new JMenuBar();
        JMenu datei = new JMenu("Datei");

        MeinListener listener = new MeinListener();

        JMenuItem neu = new JMenuItem("Neu");
        neu.setIcon(new ImageIcon("icons/new24.gif"));
        neu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        neu.setActionCommand("neu");
        neu.addActionListener(listener);
        datei.add(neu);

        JMenuItem oeffnen = new JMenuItem("Öffnen");
        oeffnen.setIcon(new ImageIcon("icons/open24.gif"));
        oeffnen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        oeffnen.setActionCommand("oeffnen");
        oeffnen.addActionListener(listener);
        datei.add(oeffnen);

        JMenuItem speichern = new JMenuItem("Speichern");
        speichern.setIcon(new ImageIcon("icons/save24.gif"));
        speichern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        speichern.setActionCommand("speichern");
        speichern.addActionListener(listener);
        datei.add(speichern);

        JMenuItem beenden = new JMenuItem("Beenden");
        beenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        beenden.setActionCommand("beenden");
        beenden.addActionListener(listener);
        datei.add(beenden);

        menue.add(datei);

        this.setJMenuBar(menue);
    }

    private void symbolleiste() {

        leiste.setFloatable(false);

        MeinListener listener = new MeinListener();

        neuBT.setIcon(new ImageIcon("icons/new24.gif"));
        neuBT.setToolTipText("Erstellt ein neues Dokument");
        neuBT.setActionCommand("neu");
        neuBT.addActionListener(listener);

        oeffnenBT.setIcon(new ImageIcon ("icons/open24.gif"));
        oeffnenBT.setToolTipText("Öffnet ein Dokument");
        oeffnenBT.setActionCommand("oeffnen");
        oeffnenBT.addActionListener(listener);

        speicherBT.setIcon(new ImageIcon ("icons/save24.gif"));
        speicherBT.setToolTipText("Speichert ein Dokument");
        speicherBT.setActionCommand("speichern");
        speicherBT.addActionListener(listener);


    }

    private void neu() {
        int meldung = JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich eine neue Datei erstellen?");
if (meldung == JOptionPane.YES_OPTION) {
    textArea1.setText("");
}
    }

    private void oeffnen() {

        int chooser = fileChooser.showOpenDialog(this);
        if (chooser == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileReader fr = new FileReader(file)) {
                textArea1.read(fr, null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Fehler beim Öffnen!");
            }
        }
    }

    private void speichern() {

        int save = fileChooser.showSaveDialog(this);
        if (save == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();

            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            try (FileWriter fw = new FileWriter(file);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                textArea1.write(bw);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this , "Fehler beim Speichern!");
            }
        }

    }

    private void beenden() {
        int meldung = JOptionPane.showConfirmDialog(this, "Wollen Sie die Anwendung wirklich beenden?");
        if (meldung == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}