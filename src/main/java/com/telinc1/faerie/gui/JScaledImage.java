/*
 * Copyright (c) 2018 Telinc1
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.telinc1.faerie.gui;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class JScaledImage extends JComponent {
    /**
     * The image which is currently being displayed.
     */
    private BufferedImage image;

    @Override
    public void paintComponent(Graphics destination){
        if(this.isOpaque()){
            destination.setColor(this.getBackground());
            destination.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        BufferedImage image = this.getImage();

        if(image == null){
            return;
        }

        int width = image.getWidth();
        int height = image.getHeight();

        if(width > height){
            width = this.getWidth();
            height = (width * height) / image.getWidth();
        }else{
            height = this.getHeight();
            width = (width * height) / image.getHeight();
        }

        Graphics2D graphics = (Graphics2D)destination;

        graphics.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
        );

        graphics.drawImage(
            image,
            (this.getWidth() - width) / 2,
            (this.getHeight() - height) / 2,
            width,
            height,
            this.getBackground(),
            null
        );
    }

    /**
     * Returns the currently displayed image.
     *
     * @return a {@link BufferedImage} for the current image
     */
    public BufferedImage getImage(){
        return this.image;
    }

    /**
     * Sets a new image to display.
     *
     * @param image the new image to set
     * @return the component, for chaining
     */
    public JScaledImage setImage(BufferedImage image){
        this.image = image;
        return this;
    }

    /**
     * Loads a new image into the component.
     *
     * @param name the path of the image to load, relative to {@code com.telinc1.faerie.icons}
     * @return the component, for chaining
     */
    public JScaledImage loadImage(String name) throws IOException{
        URL imageURL = this.getClass().getResource("/com/telinc1/faerie/icons/" + name);
        BufferedImage image = ImageIO.read(imageURL);

        this.setImage(image);
        return this;
    }
}
