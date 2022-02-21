package nl.lunarum.javachess.application;

import nl.lunarum.javachess.engine.AutoPlay;
import nl.lunarum.javachess.engine.ChessBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.JOptionPane.showOptionDialog;

public class ButtonPanel extends JPanel {
    public class ChessButton extends JButton {
        public ChessButton(String title) {
            super(title);
            setMinimumSize(new Dimension(150, 50));
        }
    }
    private final ChessBoard board;
    private final BoardPanel boardPanel;
    private final NotationPanel notationPanel;

    public ButtonPanel(ChessBoard chessBoard, BoardPanel boardPanel, NotationPanel notationPanel) {
        this.board = chessBoard;
        this.boardPanel = boardPanel;
        this.notationPanel = notationPanel;

//        setLayout(new BorderLayout());

        var button = new ChessButton("New");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Ja","Nee"};
                if (showOptionDialog((Component) getParent(),"Zeker weten?", "Nieuw spel",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]) == 0) {
                    board.setup();
                    boardPanel.repaint();
                    notationPanel.repaint();
                }
            }
        });
        this.add(button);

        button = new ChessButton("Play");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoPlay play = new AutoPlay((board));
                var ply = play.getBestPly(5);
                if (ply != null) {
                    board.playPly(ply);
                    boardPanel.repaint();
                    notationPanel.repaint();
                }
            }
        });
        this.add(button);
    }
}
