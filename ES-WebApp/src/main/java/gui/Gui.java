package gui;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import converter.Converter;

public class Gui {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int SIDEBAR_WIDTH = 200;


	private JFrame frame;
	private JButton chooseFile, convertFile;
	private JScrollPane scrollpane;
	private JPanel buttons;
	private JLabel buttonsTitle;
	private JTextArea filePathTextArea;
	
	private boolean fileChosen = false;


	public Gui() {
		frame = new JFrame("Calendar App");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		addFrameContent();
		frame.pack();
	}

	private void addFrameContent() {

		createHtmlPane();

		buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.setPreferredSize(new Dimension(SIDEBAR_WIDTH, HEIGHT));

		buttonsTitle = new JLabel("Sidebar", SwingConstants.CENTER);
		buttonsTitle.setPreferredSize(new Dimension(SIDEBAR_WIDTH,25));
		buttons.add(buttonsTitle);

		chooseFile = new JButton("Choose File");
		buttons.add(chooseFile);

		convertFile = new JButton("Convert File");
		convertFile.setEnabled(false);
		buttons.add(convertFile);

		filePathTextArea = new JTextArea();
		filePathTextArea.setEditable(false);
		filePathTextArea.setPreferredSize(new Dimension(SIDEBAR_WIDTH-10, 200));
		buttons.add(filePathTextArea);
		
		addListeners();
		
		frame.add(scrollpane, BorderLayout.CENTER);
		frame.add(buttons, BorderLayout.WEST);
	}
	
	public void addListeners() {
		chooseFile.addActionListener(e -> chooseFileAction());
		convertFile.addActionListener(e -> convertFileAction());
	}


	public void createHtmlPane() {
		JEditorPane jEditorPane = new JEditorPane();
		jEditorPane.setEditable(false);
		URL url = Gui.class.getResource("index.html");

		try {
			jEditorPane.setPage(url);
		} catch (IOException e) {
			jEditorPane.setContentType("text/html");
			jEditorPane.setText("<html>Page not found.</html>");
		}

		scrollpane = new JScrollPane(jEditorPane);
		scrollpane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	public void chooseFileAction() {
	    JFileChooser fileChooser = new JFileChooser();
	    
//	    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
	    
	    int result = fileChooser.showOpenDialog(frame);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File selectedFile = fileChooser.getSelectedFile();
	        // Display the file path in the text field
	        filePathTextArea.setText(selectedFile.getAbsolutePath());
	        fileChosen = true;
	    } else {
	        fileChosen = false;
	    }
	    convertFile.setEnabled(fileChosen);
	}


	// Method to get the file extension
	private String getFileExtension(String fileName) {
	    int index = fileName.lastIndexOf(".");
	    if (index > 0 && index < fileName.length() - 1) {
	        return fileName.substring(index + 1).toLowerCase();
	    }
	    return "";
	}
	
	
	private void convertFileAction() {
	    // create a file chooser to allow the user to select where to save the converted file
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

	    // display the file chooser and get the user's selection
	    int result = fileChooser.showSaveDialog(frame);

	    if (result == JFileChooser.APPROVE_OPTION) {
	        // get the selected file from the file chooser
	        File outputFile = fileChooser.getSelectedFile();

	        // determine the input file's extension
	        String inputFilepath = filePathTextArea.getText();
	        String inputExtension = getFileExtension(inputFilepath);

	        // determine the output file's extension based on the input file's extension
	        String outputExtension;
	        if (inputExtension.equalsIgnoreCase("csv")) {
	            outputExtension = "json";
	        } else if (inputExtension.equalsIgnoreCase("json")) {
	            outputExtension = "csv";
	        } else {
	            showMessageDialog(frame, "Invalid file type. Only CSV and JSON files are supported.");
	            return;
	        }

	        // create the output file by replacing the input file's extension with the output extension
	        String outputFilename = outputFile.getName();
	        if (!outputFilename.toLowerCase().endsWith("." + outputExtension)) {
	            outputFilename += "." + outputExtension;
	            outputFile = new File(outputFile.getParentFile(), outputFilename);
	        }

	        // convert the file and save it to the output file location
			if (inputExtension.equalsIgnoreCase("csv")) {
				Converter.csvToJson(inputFilepath, outputFile.getAbsolutePath());
			} else if (inputExtension.equalsIgnoreCase("json")) {
				Converter.jsonToCsv(inputFilepath, outputFile.getAbsolutePath());
			}
			showMessageDialog(frame, "File Converted");
	    }
	}



	private void open() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Gui app = new Gui();
		app.open();
	}
}