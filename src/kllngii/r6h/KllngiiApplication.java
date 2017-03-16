package kllngii.r6h;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public abstract class KllngiiApplication {

    /**
     * Versieht eine {@link JComponent} mit einem unsichtbaren Rand von soundso vielen Pixeln;
     * so wie bei Padding per CSS in HTML-Seiten. <br/>
     * @return  Entweder die Komponente selbst (mit neuer Border), oder ein neues JPanel mit entsprechender
     *          Border, das wiederum die Komponente enthält.
     */
    protected JComponent padding(JComponent comp, int top, int right, int bottom, int left) {
        Border outerBorder = new EmptyBorder(top, left, bottom, right);
        if (comp.getBorder() == null)
            comp.setBorder(outerBorder);
        else {
            // *Eigentlich* sollte man jetzt mit einer CompoundBorder auskommen.
            // Bei Buttons aber sieht das falsch aus.
            if (comp instanceof JButton) {
                JPanel panel = new JPanel();
                panel.add(comp);
                panel.setBorder(outerBorder);
                panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
                return panel;
            }
            else
                comp.setBorder(new CompoundBorder(outerBorder, comp.getBorder()));
        }
        
        return comp;
    }

    protected JComponent paddingLeft(JComponent comp, int left) {
        return padding(comp, 0, 0, 0, left);
    }

    protected JComponent paddingRight(JComponent comp, int right) {
        return padding(comp, 0, right, 0, 0);
    }

    protected JComponent padding(JComponent comp, int topAndBottom, int leftAndRight) {
            return padding(comp, topAndBottom, leftAndRight, topAndBottom, leftAndRight);
    }

    protected JComponent padding(JComponent comp, int allSides) {
        return padding(comp, allSides, allSides, allSides, allSides);
    }

}