package GUI;

import java.awt.*;

public class ImageButton extends Canvas {
    private final Image baseImage, selectImage;
    private boolean selected;

    public ImageButton(String bImage, String sImage) {
        baseImage = Toolkit.getDefaultToolkit().getImage(bImage);
        selectImage = Toolkit.getDefaultToolkit().getImage(sImage);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200,100);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Image img = selected ? selectImage : baseImage;
        g.drawImage(img,0,0,getWidth(),getHeight(),this);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
