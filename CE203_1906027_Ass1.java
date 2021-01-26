package appAssignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// Incomplete ID class for CE203 Assignment 1
// Date: 12/10/2020
// Author: f.Doctor
class ID implements Comparable<ID>{

    // id attribute
    int id = 000000;

    // constructor should input an ID as a String or int and set it to the attribute id - to be modified
    public ID(String myID)
    {
        id = Integer.parseInt(myID);
    }


    // gets a stored ID
    public int getID() {
        return id;
    }


    // sets the input parameter to an ID this can be modified to input a string in which case you will need to convert
    // the parameter to an int
    public void setID(int inputID) {
        id = inputID;
    }


    @Override
    // method used for comparing ID objects based on stored ids, you need to complete the method
    public int compareTo(ID o) {
        return Integer.compare(id, o.id);
    }

    // outputs a string representation of the object
    public String toString()
    {
        return ("ID = "+id);
    }
}


public class CE203_1906027_Ass1 extends JPanel implements ListSelectionListener {
    private JList list; //list used for displaying the array of IDs
    //private DefaultListModel listModel;
    private ArrayList IDArray; //list used for storing input IDs

    private JButton deleteBtn;
    private JButton sortBtn;
    private JButton clearBtn;
    private JButton displayBtn;

    //input text field
    private JTextField input;

    //text fields for rgb of colors
    private JTextField rgb1;
    private JTextField rgb2;
    private JTextField rgb3;

    public CE203_1906027_Ass1() { //start of main method
        super(new BorderLayout());

        IDArray = new ArrayList();

        // Create the list and put it in a scroll pane.
        list = new JList(IDArray.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        list.setVisible(false); //make list invisible before click
        JScrollPane listScrollPane = new JScrollPane(list);

        //Add ID button implementation:
        JButton addBtn = new JButton("Add ID");
        BtnListener addBtnListener = new BtnListener(addBtn);
        addBtn.addActionListener(addBtnListener);
        addBtn.setEnabled(false);

        //Delete ID button implementation:
        deleteBtn = new JButton("Delete ID");
        deleteBtn.addActionListener(new ActListener());

        //Sort ID button implementation:
        sortBtn = new JButton("Sort ID");
        sortBtn.addActionListener(new ActListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          //make sure the .sort function accepts the list by establishing an ArrayList
                                          //Collections.sort(Collections.list(listModel.elements()));
                                          Collections.sort(IDArray);// sorts list in ascending order
                                          list.setModel(ArrayListModel(IDArray)); //display sorted list on button click
                                      }
                                  });

        //Clear ID button implementation:
        clearBtn = new JButton("Clear List");
        clearBtn.addActionListener(new ActListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IDArray.clear(); // remove all elements
            }
        });

        //Display ID list button implementation:
        displayBtn = new JButton("Display List");
        displayBtn.addActionListener(new ActListener() { //when clicked display chosen colors
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Colors()){
                    int rgb1Int = Integer.parseInt(rgb1.getText());
                    int rgb2Int = Integer.parseInt(rgb2.getText());
                    int rgb3Int = Integer.parseInt(rgb3.getText());

                    Color textColor = new Color(rgb1Int, rgb2Int, rgb3Int);

                    list.setForeground(textColor);
                }
                list.setModel(ArrayListModel(IDArray));
                list.setVisible(true);
            }
        });

        //initialise input text field
        input = new JTextField(10);
        input.addActionListener(addBtnListener);
        input.getDocument().addDocumentListener(addBtnListener);

        //initialise color text fields
        rgb1 = new JTextField(3);
        rgb2 = new JTextField(3);
        rgb3 = new JTextField(3);
        //JTextField rgb: Arrays.asList(rgb1, rgb2, rgb3); //make sure all text fields accept the same input

        // Create a panel with buttons
        JPanel butPanel = new JPanel();
        butPanel.add(displayBtn);
        butPanel.add(deleteBtn);
        butPanel.add(addBtn);
        butPanel.add(sortBtn);
        butPanel.add(clearBtn);
        add(butPanel, BorderLayout.SOUTH);

        //Create input text field panel
        JPanel inpPanel = new JPanel();
        JLabel inpLabel = new JLabel("Enter ID: ");
        inpLabel.setLabelFor(input);
        inpPanel.add(inpLabel);
        inpPanel.add(input);
        add(inpPanel, BorderLayout.NORTH);

        // Create a panel with color text fields
        JPanel rgbPanel = new JPanel();
        JLabel rgbLabel = new JLabel("Enter RGB values: ");
        rgbLabel.setLabelFor(rgb1);
        rgbPanel.add(rgbLabel);
        rgbPanel.add(rgb1);
        rgbPanel.add(rgb2);
        rgbPanel.add(rgb3);
        add(rgbPanel, BorderLayout.EAST);
        add(listScrollPane, BorderLayout.CENTER);

    }

    //array storing IDs for sorting
    public DefaultListModel ArrayListModel(ArrayList<ID> arrayList){
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < arrayList.size(); i++)
        {
            listModel.addElement(arrayList.get(i));
        }
        return listModel;
    }

    class ActListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // check for a selection
            // remove what is selected
            int index = list.getSelectedIndex();
            IDArray.remove(index);

            int size = IDArray.size();

            if (size == 0) { // If list is empty, disable "Delete ID" button
                deleteBtn.setEnabled(false);

            } else { // Select the index of ID to be deleted
                if (index == IDArray.size()) {
                    // removed item in last position
                    index--;
                }
                //tried to remove multiple indexes if one selection has duplicates
                /*else {
                    for ((listModel.contains(index)) {
                            remove(index);
                    }*/

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    // This listener is shared by the text fields and the addID button.
    class BtnListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public BtnListener(JButton button) {
            this.button = button;
        }

        // Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            //checks if input text fields are correct:
            //check whether the length of the input is 6
            if (input.getText().trim().length() == 6) {
                try { //if it is, continue:
                    //String id = input.getText();
                    IDArray.add(Integer.parseInt(input.getText())); //check if the input is an integer
                    JOptionPane.showMessageDialog(input, "ID " + input.getText() + " was added to the list."); //if it is, add the ID to the list
                } catch (NumberFormatException n) { //if not, display error message
                    JOptionPane.showMessageDialog(input, "ID " + input.getText() + " was not added to the list as it is not valid.");
                }
            } else { //if length is not satisfied, display error message
                JOptionPane.showMessageDialog(input, "Please make sure your ID contains 6 characters!");
            }

            // Reset the text field.
            input.requestFocusInWindow();
            input.setText("");
        }

        // Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        // Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        // Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //checks if rgb text fields are correct upon clicking Display ID button:
    public boolean Colors() {
        //make array out of text fields to prevent repetition
        for (JTextField rgb: Arrays.asList(rgb1, rgb2, rgb3))
        {
            if (rgb.getText().isBlank()) return false; //do not allow blank input
            //check whether the rgb inputs are between 1 and 3 numbers
            if ((rgb.getText().trim().length() >= 0 && rgb.getText().trim().length() <= 3)) {
                try { //if they are, continue:
                    int rgbNum = Integer.parseInt(rgb.getText()); //check if the input is an integer
                    //check if the integer is between 1-255:
                    if (rgbNum < 1 || rgbNum > 255) {
                        JOptionPane.showMessageDialog(rgb, "Please check if your RGB value is between 1-255!");
                        return false;
                    }

                } catch (NumberFormatException n) {
                    //if not, display error message
                    JOptionPane.showMessageDialog(rgb, "Please only input numerical values.");
                    return false;
                }
            } else { //if length is not satisfied, display error message
                JOptionPane.showMessageDialog(rgb, "Please check if RGB value contains 1-3 characters!");
                return false;
            }
        }
        return true;
    }

    // This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                // No selection, disable fire button.
                deleteBtn.setEnabled(false);

            } else {
                // Selection, enable the fire button.
                deleteBtn.setEnabled(true);
            }
        }
    }

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Input IDs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        JComponent newContentPane = new CE203_1906027_Ass1(); //main
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
