import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class MyJFrame extends JFrame implements ActionListener {

    JMenuItem about;
    JMenuItem exit;
    JButton buttonOk;
    JTextField textField;
    ArrayList<String> tokenList = new ArrayList<String>();

    public MyJFrame(){
        super();
        initFrame();
    }
    private void initFrame() {

        this.setTitle("TP Compilation");
        this.setSize(500,400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        about = new JMenuItem("About TP");
        exit = new JMenuItem("Quit TP");

        about.addActionListener(this);
        exit.addActionListener(this);

        menuBar.add(file);
        file.add(about);
        file.addSeparator();
        file.add(exit);
        this.setJMenuBar(menuBar);

        Container container = this.getContentPane();
        container.setLayout(null);

        JLabel label = new JLabel("Mini projet - Compilation");
        label.setFont(new Font("Serif",Font.PLAIN,30));
        label.setBounds(100,20,400,40);
        container.add(label);

        JLabel label2 = new JLabel("Entrer Votre Expression Arithmetique:");
        label2.setFont(new Font("Serif",Font.PLAIN,24));
        label2.setBounds(50,100,400,40);
        container.add(label2);

        textField = new JTextField();
        textField.setColumns(20);
        textField.setBorder(BorderFactory.createTitledBorder("Expression arithmetique"));
        textField.setHorizontalAlignment(JTextField.LEFT);
        textField.setFont(new Font("Cartograph",Font.PLAIN,18));
        textField.setBounds(50,140,400,60);
        container.add(textField);

        buttonOk = new JButton("OK");
        buttonOk.setBounds(220,220,70,40);
        buttonOk.setFont(new Font("Serif",Font.BOLD,24));
        buttonOk.addActionListener(this);
        buttonOk.setMnemonic(KeyEvent.VK_ENTER);
        container.add(buttonOk);

        JLabel label3 = new JLabel("Exemple: x = a * 2 / (25 + c * (3.2 - y));");
        label3.setFont(new Font("Serif",Font.PLAIN,18));
        label3.setBounds(50,270,400,40);
        container.add(label3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == buttonOk){
            if(textField.getText().isBlank())
                JOptionPane.showConfirmDialog(
                        this,
                        "Saisir une Expression",
                        "Expression",
                        JOptionPane.PLAIN_MESSAGE);
            else {
                Expression expression = new Expression(textField.getText());
                JOptionPane.showConfirmDialog(
                        this,
                        expression, "Expression",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
        if (e.getSource() == about){
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        JOptionPane.showMessageDialog(
                                null,
                                "\tMOULAI AZEDDINE\n\tBELGHIT LOUNES\n\tROUABAH OUALID\n",
                                "Akatsuki",JOptionPane.PLAIN_MESSAGE);
                    }
                });
        }
        if (e.getSource()==exit){
            System.exit(0);
        }
    }
}
