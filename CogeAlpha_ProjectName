import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SimpleChatbot {

    // FAQ memory (acts as training data)
    private static final Map<String, String> knowledgeBase = new HashMap<>();

    public static void main(String[] args) {
        // Preload some FAQs
        knowledgeBase.put("hello", "Hi there! How can I help you?");
        knowledgeBase.put("hi", "Hello! What can I do for you?");
        knowledgeBase.put("how are you", "I'm just a bot, but I'm doing great!");
        knowledgeBase.put("what is your name", "I'm a simple Java Chatbot 🤖");
        knowledgeBase.put("bye", "Goodbye! Have a nice day!");
        knowledgeBase.put("what is java", "Java is a powerful programming language used for many applications.");

        // Create GUI
        JFrame frame = new JFrame("AI Chatbot");
        JTextArea chatArea = new JTextArea(18, 45);
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        JButton trainButton = new JButton("Train Bot");

        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // Layout
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(trainButton);
        buttonPanel.add(sendButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputField, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Event: Send message
        sendButton.addActionListener(e -> {
            String userInput = inputField.getText().trim().toLowerCase();
            if (!userInput.isEmpty()) {
                chatArea.append("You: " + userInput + "\n");
                String response = getResponse(userInput);
                chatArea.append("Bot: " + response + "\n\n");
                inputField.setText("");
            }
        });

        // Enter key sends message
        inputField.addActionListener(e -> sendButton.doClick());

        // Train bot: add new question-answer pair
        trainButton.addActionListener(e -> {
            String question = JOptionPane.showInputDialog(frame, "Enter new question:");
            String answer = JOptionPane.showInputDialog(frame, "Enter bot’s answer:");
            if (question != null && answer != null && !question.isBlank() && !answer.isBlank()) {
                knowledgeBase.put(question.toLowerCase(), answer);
                chatArea.append("Bot learned: \"" + question + "\" → \"" + answer + "\"\n\n");
            }
        });

        chatArea.append("Bot: Hello! I'm your simple AI Chatbot. Ask me something.\n\n");
    }

    // Simple NLP logic (keyword matching)
    private static String getResponse(String input) {
        // Basic preprocessing
        input = input.replaceAll("[^a-zA-Z ]", "").toLowerCase();

        // Try to find direct match
        for (String key : knowledgeBase.keySet()) {
            if (input.contains(key)) {
                return knowledgeBase.get(key);
            }
        }

        // Default fallback
        return "I'm not sure about that. You can train me using the 'Train Bot' button!";
    }
}

