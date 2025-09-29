import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;



public class TEditor extends JFrame {
    private JPanel panel1;
    private JTextArea textArea1;
    private JScrollPane pane1;
    private JToolBar leiste;
    private JButton neuBT;
    private JButton oeffnenBT;
    private JButton speicherBT;
    private JTextField textField1;
    private JButton suchen;
    private JTextField textField2;
    private JButton ersetzen;

    //Listener hinzufügen
    MeinListener listener = new MeinListener();
    //Filechooser hinzufügen
    JFileChooser fileChooser = new JFileChooser();
    //Filefilter hinzufügen, es sollen nur txt Dateien angezeigt werden
    FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Textdateien (*.txt)", "txt");



    //Listener-Klasse
    class MeinListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Methoden aufrufen bei ausgeführten Aktionen
            if (e.getActionCommand().equals("neu"))
                neu();
            if (e.getActionCommand().equals("oeffnen"))
                oeffnen();
            if (e.getActionCommand().equals("speichern"))
                speichern();
            if (e.getActionCommand().equals("beenden"))
                beenden();
            if (e.getActionCommand().equals("Suchen"))
                suchen();
            if (e.getActionCommand().equals("Ersetzen"))
                Ersetzen();
        }
    }

    public TEditor(String titel) {
        super(titel);
        //Fenstereinstellungen
        setContentPane(panel1);
        setVisible(true);
        setSize(640, 480);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Filter für Textdateien erstellen
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        //Menü und Toolbar erstellen
        symbolleiste();
        menu();
    }

    private void menu() {

        JMenuBar menue = new JMenuBar();
        JMenu datei = new JMenu("Datei");

        // Menüpunkt Neu
        JMenuItem neu = new JMenuItem("Neu");
        neu.setIcon(new ImageIcon("icons/new24.gif"));
        neu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        neu.setActionCommand("neu");
        neu.addActionListener(listener);
        datei.add(neu);

        // Menüpunkt Öffnen
        JMenuItem oeffnen = new JMenuItem("Öffnen");
        oeffnen.setIcon(new ImageIcon("icons/open24.gif"));
        oeffnen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        oeffnen.setActionCommand("oeffnen");
        oeffnen.addActionListener(listener);
        datei.add(oeffnen);

        // Menüpunkt Speichern
        JMenuItem speichern = new JMenuItem("Speichern");
        speichern.setIcon(new ImageIcon("icons/save24.gif"));
        speichern.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        speichern.setActionCommand("speichern");
        speichern.addActionListener(listener);
        datei.add(speichern);

        // Menüpunkt Beenden
        JMenuItem beenden = new JMenuItem("Beenden");
        beenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        beenden.setActionCommand("beenden");
        beenden.addActionListener(listener);
        datei.add(beenden);

        this.setJMenuBar(menue);
        menue.add(datei);
    }

    private void symbolleiste() {
        // Toolbar fixieren
        leiste.setFloatable(false);

        // Button Neu
        neuBT.setIcon(new ImageIcon("icons/new24.gif"));
        neuBT.setToolTipText("Erstellt ein neues Dokument");
        neuBT.setActionCommand("neu");
        neuBT.addActionListener(listener);

        // Button Öffnen
        oeffnenBT.setIcon(new ImageIcon ("icons/open24.gif"));
        oeffnenBT.setToolTipText("Öffnet ein Dokument");
        oeffnenBT.setActionCommand("oeffnen");
        oeffnenBT.addActionListener(listener);

        // Button Speichern
        speicherBT.setIcon(new ImageIcon ("icons/save24.gif"));
        speicherBT.setToolTipText("Speichert ein Dokument");
        speicherBT.setActionCommand("speichern");
        speicherBT.addActionListener(listener);

        // Button Suchen
        suchen.setActionCommand("Suchen");
        suchen.addActionListener(listener);

        // Button Ersetzen
        ersetzen.setActionCommand("Ersetzen");
        ersetzen.addActionListener(listener);


    }

    private void suchen() {
        // Markierungen im editor entfernen
        textArea1.getHighlighter().removeAllHighlights();
        // Text aus den Feldern in einen String schreiben
        String suchentext = textField1.getText();
        String inhalt = textArea1.getText();

        // Wenn suchentext leer ist, nichts machen
        if (suchentext == null || suchentext.isEmpty()) {
            return;
        }

        Highlighter highlighter = textArea1.getHighlighter();
        // Startposition für Suche
        int pos = 0;
        // Zähler für Treffer
        int gefunden = 0;


        while ((pos = inhalt.indexOf(suchentext, pos)) >= 0) {
            try {
                // Treffer im Text Gelb markieren
                highlighter.addHighlight(pos, pos + suchentext.length(),
                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW));
                pos += suchentext.length();
                gefunden++;
                // Fehler bei ungültiger Position im Terminal ausgeben
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
        // Meldung, wenn nichts gefunden wurde
        if(gefunden == 0){
            JOptionPane.showMessageDialog(this, suchentext + " wurde nicht gefunden" );
        }
    }

    private void Ersetzen() {
        // Text aus den Feldern in einen String schreiben
        String suchentext = textField1.getText();
        String ersetzentext = textField2.getText();

        // Wenn suchentext leer ist, Meldung ausgeben
        if (suchentext == null || suchentext.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Es wurde kein Text ausgewählt");
            return;
        }
        // Gesamten text in einen String schreiben und austauschen
        String textersetzt = textArea1.getText().replace(suchentext, ersetzentext);
        // Text mit ersetzungen in das Feld schreiben
        textArea1.setText(textersetzt);
        // Meldung nach dem Ersetzen anzeigen
        JOptionPane.showMessageDialog(this, suchentext + " wurde durch " + ersetzentext + " ersetzt");


    }


    private void neu() {
        // Abfrage, ob eine neue Datei, erstellt werden soll
        int meldung = JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich eine neue Datei erstellen?");
        if (meldung == JOptionPane.YES_OPTION) {
            // Wenn ja ausgewählt, Feld wird geleert
            textArea1.setText("");
        }
        }

    private void oeffnen() {
        // Dateidialog anzeigen
        int chooser = fileChooser.showOpenDialog(this);
        if (chooser == JFileChooser.APPROVE_OPTION) {
            // Datei nach Auswahl holen
            File file = fileChooser.getSelectedFile();
            // Datei zum Lesen öffnen, Inhalt ins Feld einfügen
            try (FileReader fr = new FileReader(file)) {
                textArea1.read(fr, null);
            } catch (Exception ex) {
                // Meldung anzeigen, wenn Datei nicht geöffnet werden kann
                JOptionPane.showMessageDialog(this, "Fehler beim Öffnen!");
            }
        }
    }

    private void speichern() {
        // Dateidialog anzeigen
        int save = fileChooser.showSaveDialog(this);
        if (save == JFileChooser.APPROVE_OPTION) {
            // Datei nach Auswahl holen
            File file = fileChooser.getSelectedFile();
            // Wenn Datei keine .txt Endung hat, .txt hinzufügen
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }
            // Datei zum Schreiben öffnen
            try (FileWriter fw = new FileWriter(file)) {
                // Text aus dem Feld in die Datei schreiben
                textArea1.write(fw);
            } catch (Exception ex) {
                // Meldung anzeigen, wenn Datei nicht gespeichert werden kann
                JOptionPane.showMessageDialog(this , "Fehler beim Speichern!");
            }
        }

    }

    private void beenden() {
        // Meldung vor Beenden
        int meldung = JOptionPane.showConfirmDialog(this, "Wollen Sie die Anwendung wirklich beenden?");
        // Wenn ja ausgewählt, Anwendung wird beendet
        if (meldung == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}