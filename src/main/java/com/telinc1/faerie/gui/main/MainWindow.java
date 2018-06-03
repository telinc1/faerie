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

package com.telinc1.faerie.gui.main;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.telinc1.faerie.Application;
import com.telinc1.faerie.Preferences;
import com.telinc1.faerie.Resources;
import com.telinc1.faerie.gui.DecimalFormatter;
import com.telinc1.faerie.gui.HexadecimalFormatter;
import com.telinc1.faerie.gui.JPaletteView;
import com.telinc1.faerie.gui.JScaledImage;
import com.telinc1.faerie.gui.chooser.ConfigurationChooser;
import com.telinc1.faerie.gui.main.menu.MenuBar;
import com.telinc1.faerie.sprite.EnumSpriteSubType;
import com.telinc1.faerie.sprite.EnumSpriteType;
import com.telinc1.faerie.sprite.EnumStatusHandling;
import com.telinc1.faerie.sprite.Sprite;
import com.telinc1.faerie.sprite.SpriteBehavior;
import com.telinc1.faerie.sprite.provider.BlankProvider;
import com.telinc1.faerie.sprite.provider.ConfigurationProvider;
import com.telinc1.faerie.sprite.provider.LoadingException;
import com.telinc1.faerie.sprite.provider.Provider;
import com.telinc1.faerie.sprite.provider.ProvisionException;
import com.telinc1.faerie.sprite.provider.ROMProvider;
import com.telinc1.faerie.sprite.provider.SavingException;
import com.telinc1.faerie.util.TypeUtils;
import com.telinc1.faerie.util.locale.Warning;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * The main Faerie editor window.
 *
 * @author Telinc1
 * @since 1.0.0
 */
public class MainWindow extends JFrame {
    /**
     * The parent application of the window.
     */
    private Application application;

    /**
     * The menu bar which the window displays.
     */
    private MenuBar menuBar;

    /**
     * The {@link JFileChooser} used for all opening and saving operations in
     * the menu.
     */
    private ConfigurationChooser configurationChooser;

    /**
     * The currently loaded sprite provider.
     */
    private Provider provider;

    private JPanel contentPanel;
    private JTabbedPane tabbedPane;
    private JPanel spritePanel;
    private JPanel displayPanel;

    private JScrollPane spriteScrollPane;

    private JComboBox<String> spriteSelectionComboBox;

    private JComboBox<Object> typeComboBox;
    private JComboBox<Object> subtypeComboBox;
    private JFormattedTextField actsLikeTextField;
    private JTextField firstASMTextField;
    private JTextField secondASMTextField;

    @BehaviorBit("")
    private JComboBox<String> objectClippingComboBox;

    @BehaviorBit("canBeJumpedOn")
    private JCheckBox canBeJumpedOnCheckBox;

    @BehaviorBit("diesWhenJumpedOn")
    private JCheckBox diesWhenJumpedOnCheckBox;

    @BehaviorBit("hopInShells")
    private JCheckBox hopInKickShellsCheckBox;

    @BehaviorBit("disappearInSmoke")
    private JCheckBox disappearInACloudCheckBox;

    @BehaviorBit("")
    private JComboBox<String> spriteClippingComboBox;

    @BehaviorBit("useShellAsDeathFrame")
    private JCheckBox useShellAsDeathCheckBox;

    @BehaviorBit("fallsWhenKilled")
    private JCheckBox fallsWhenKilledCheckBox;

    @BehaviorBit("useSecondGraphicsPage")
    private JCheckBox useSecondGraphicsPageCheckBox;

    @BehaviorBit("")
    private JComboBox<String> paletteComboBox;

    @BehaviorBit("disableFireballKilling")
    private JCheckBox disableFireballKillingCheckBox;

    @BehaviorBit("disableCapeKilling")
    private JCheckBox disableCapeKillingCheckBox;

    @BehaviorBit("disableWaterSplash")
    private JCheckBox disableWaterSplashCheckBox;

    @BehaviorBit("disableSecondaryInteraction")
    private JCheckBox disableSecondaryInteractionCheckBox;

    @BehaviorBit("processIfDead")
    private JCheckBox executeWhileBeingKilledCheckBox;

    @BehaviorBit("invincibleToPlayer")
    private JCheckBox invincibleToPlayerCheckBox;

    @BehaviorBit("processWhileOffscreen")
    private JCheckBox executeIfOffscreenCheckBox;

    @BehaviorBit("skipShellIfStunned")
    private JCheckBox donTChangeIntoCheckBox;

    @BehaviorBit("disableKicking")
    private JCheckBox canTBeKickedCheckBox;

    @BehaviorBit("processInteractionEveryFrame")
    private JCheckBox processInteractionEveryFrameCheckBox;

    @BehaviorBit("isPowerup")
    private JCheckBox givesPowerupWhenEatenCheckBox;

    @BehaviorBit("disableDefaultInteraction")
    private JCheckBox donTUseDefaultCheckBox;

    @BehaviorBit("inedible")
    private JCheckBox inedibleCheckBox;

    @BehaviorBit("stayInMouth")
    private JCheckBox stayInYoshiSCheckBox;

    @BehaviorBit("weirdGroundBehavior")
    private JCheckBox weirdGroundBehaviorCheckBox;

    @BehaviorBit("disableSpriteInteraction")
    private JCheckBox donTInteractWithCheckBox;

    @BehaviorBit("preserveDirection")
    private JCheckBox donTChangeDirectionCheckBox;

    @BehaviorBit("disappearOnGoal")
    private JCheckBox donTTurnIntoCheckBox;

    @BehaviorBit("spawnsSpriteWhenStunned")
    private JCheckBox spawnsANewSpriteCheckBox;

    @BehaviorBit("disableObjectInteraction")
    private JCheckBox donTInteractWithCheckBox1;

    @BehaviorBit("platformPassableFromBelow")
    private JCheckBox makePlatformPassableFromCheckBox;

    @BehaviorBit("ignoreGoal")
    private JCheckBox donTEraseWhenCheckBox;

    @BehaviorBit("disableSlideKilling")
    private JCheckBox canTBeKilledCheckBox;

    @BehaviorBit("takesFiveFireballs")
    private JCheckBox takes5FireballsToCheckBox;

    @BehaviorBit("canBeJumpedOnFromBelow")
    private JCheckBox canBeJumpedOnCheckBox1;

    @BehaviorBit("tallDeathFrame")
    private JCheckBox deathFrame2TilesCheckBox;

    @BehaviorBit("ignoreSilverPSwitch")
    private JCheckBox ignoreSilverPSwitchCheckBox;

    @BehaviorBit("escapeWalls")
    private JCheckBox donTGetStuckCheckBox;

    private JFormattedTextField firstPropertyTextField;
    private JFormattedTextField secondPropertyTextField;
    private JComboBox<String> statusOverrideComboBox;
    private JFormattedTextField uniqueByteTextField;
    private JFormattedTextField extraByteAmountTextField;

    private JScaledImage objectClippingImage;
    private JScaledImage spriteClippingImage;
    private JPaletteView paletteView;

    /**
     * Construct an application window, including all of its inner components.
     * <p>
     * The title and sizes are automatically set.
     * <p>
     * Note that the window is not opened.
     */
    public MainWindow(Application application){
        super(Resources.getString("main", "title"));
        this.application = application;

        this.$$$setupUI$$$();
        this.configureUIComponents();

        this.setInputEnabled(false);
        this.setContentPane(this.contentPanel);

        this.setSize(
            this.getApplication().getPreferences().get(Preferences.MAIN_WINDOW_WIDTH, 760),
            this.getApplication().getPreferences().get(Preferences.MAIN_WINDOW_HEIGHT, 600)
        );
        this.setMinimumSize(new Dimension(760, 600));

        this.setLocation(
            this.getApplication().getPreferences().get(Preferences.MAIN_WINDOW_X, 0),
            this.getApplication().getPreferences().get(Preferences.MAIN_WINDOW_Y, 0)
        );

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event){
                Point location = MainWindow.this.getLocation();
                Dimension size = MainWindow.this.getSize();

                Preferences preferences = MainWindow.this.getApplication().getPreferences();

                preferences.set(Preferences.MAIN_WINDOW_X, location.x);
                preferences.set(Preferences.MAIN_WINDOW_Y, location.y);
                preferences.set(Preferences.MAIN_WINDOW_WIDTH, size.width);
                preferences.set(Preferences.MAIN_WINDOW_HEIGHT, size.height);

                MainWindow.this.getApplication().exit(0);
            }
        });

        URL iconURL = Resources.class.getResource(Resources.PACKAGE + "/images/application.png");
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());

        this.menuBar = new MenuBar(this);
        this.setJMenuBar(this.menuBar);

        this.configurationChooser = new ConfigurationChooser(this.getApplication());

        this.createBlankFile();
    }

    /**
     * Configures UI components after they have been created.
     */
    private void configureUIComponents(){
        this.spriteScrollPane.getVerticalScrollBar().setUnitIncrement(
            this.getApplication().getPreferences().get(Preferences.SCROLL_SPEED, 14)
        );

        this.addComboBoxListener(this.spriteSelectionComboBox, index -> {
            try {
                this.loadSprite(index);
            }catch(ProvisionException exception){
                this.getApplication().getExceptionHandler().handle(this, exception);
            }
        });

        this.addComboBoxListener(this.typeComboBox, index -> {
            if(index == this.getProvider().getCurrentSprite().getType().asInteger()){
                return;
            }

            this.startModification().setType(EnumSpriteType.fromInteger(index));
            this.updateGUI();
        });

        this.addComboBoxListener(this.subtypeComboBox, index -> {
            if(index == this.getProvider().getCurrentSprite().getSubType().asInteger()){
                return;
            }

            this.startModification().setSubtype(EnumSpriteSubType.fromInteger(index));
            this.updateGUI();
        });

        HexadecimalFormatter.apply(this.actsLikeTextField, 0x0, 0xFF, value -> {
            if(this.getProvider().getCurrentSprite().getActsLike() == value){
                return;
            }

            this.startModification().setActsLike(value);
        });

        this.addTextFieldListener(this.firstASMTextField, text -> {
            if(this.getProvider().getCurrentSprite().getFirstASMFile().equals(text)){
                return;
            }

            this.startModification().setFirstASMFile(text);
        });

        this.addTextFieldListener(this.secondASMTextField, text -> {
            if(this.getProvider().getCurrentSprite().getSecondASMFile().equals(text)){
                return;
            }

            this.startModification().setSecondASMFile(text);
        });

        for(Field field : this.getClass().getDeclaredFields()){
            if(field.getType() != JCheckBox.class){
                continue;
            }

            BehaviorBit annotation = field.getDeclaredAnnotation(BehaviorBit.class);

            if(annotation == null){
                continue;
            }

            try {
                JCheckBox checkBox = (JCheckBox)field.get(this);
                Field bit = SpriteBehavior.class.getDeclaredField(annotation.value());
                checkBox.addItemListener(event -> {
                    try {
                        if(bit.getBoolean(this.getProvider().getCurrentSprite().getBehavior()) == checkBox.isSelected()){
                            return;
                        }

                        bit.setBoolean(this.startModification().getBehavior(), checkBox.isSelected());
                    }catch(IllegalAccessException e){
                        e.printStackTrace();
                    }
                });
            }catch(ReflectiveOperationException exception){
                exception.printStackTrace();
            }
        }

        this.addComboBoxListener(this.objectClippingComboBox, this::setObjectClipping);
        this.addComboBoxListener(this.spriteClippingComboBox, this::setSpriteClipping);
        this.addComboBoxListener(this.paletteComboBox, this::setSpritePalette);

        HexadecimalFormatter.apply(this.firstPropertyTextField, 0x0, 0xFF, value -> {
            if(this.getProvider().getCurrentSprite().getFirstPropertyByte() == value){
                return;
            }

            this.startModification().setFirstPropertyByte(value);
        });

        HexadecimalFormatter.apply(this.secondPropertyTextField, 0x0, 0x3F, value -> {
            if(this.getProvider().getCurrentSprite().getSecondPropertyByte() == value){
                return;
            }

            this.startModification().setSecondPropertyByte(value);
        });

        this.addComboBoxListener(this.statusOverrideComboBox, index -> {
            EnumStatusHandling handling = EnumStatusHandling.fromBits(index);

            if(this.getProvider().getCurrentSprite().getStatusHandling() == handling){
                return;
            }

            this.startModification().setStatusHandling(handling);
        });

        HexadecimalFormatter.apply(this.uniqueByteTextField, 0x0, 0xFF, value -> {
            if(this.getProvider().getCurrentSprite().getUniqueByte() == value){
                return;
            }

            this.startModification().setUniqueByte(value);
        });

        DecimalFormatter.apply(this.extraByteAmountTextField, 0x0, 0xFF, value -> {
            if(this.getProvider().getCurrentSprite().getExtraBytes() == value){
                return;
            }

            this.startModification().setExtraBytes(value);
        });
    }

    /**
     * Unloads the current provider, first making sure all changes are saved.
     *
     * @return whether the provider was unloaded
     */
    public boolean unloadProvider(){
        if(this.getProvider() == null){
            return true;
        }

        if(!this.getProvider().isModified()){
            this.setProvider(null);
            return true;
        }

        String[] options = new String[]{
            Resources.getString("main", "dialog.option.yes"),
            Resources.getString("main", "dialog.option.no"),
            Resources.getString("main", "dialog.option.cancel")
        };

        int option = JOptionPane.showOptionDialog(
            this,
            Resources.getString("main", "dialog.unsaved.content"),
            Resources.getString("main", "dialog.unsaved.title"),
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]
        );

        if(option == -1 || option == 2){
            return false;
        }

        if(option == 0){
            if(this.getProvider().getInput() != null){
                this.getConfigurationChooser().setSelectedFile(this.getProvider().getInput());
            }

            this.showSaveDialog();
        }

        this.setProvider(null);
        return true;
    }

    /**
     * Returns the {@link JFileChooser} for the File menu.
     *
     * @return the shared file chooser used for opening and saving
     */
    public ConfigurationChooser getConfigurationChooser(){
        return this.configurationChooser;
    }

    /**
     * Shows a save dialog and saves the current provider to the selected file.
     */
    public void showSaveDialog(){
        Provider provider = this.getProvider();

        if(provider == null){
            this.getApplication().getNotifier().error(this, "chooser", "save.blank");
            return;
        }

        if(this.getConfigurationChooser().showSave(this) == JFileChooser.APPROVE_OPTION){
            this.saveProvider(this.getConfigurationChooser().getSelectedFile());
        }

        this.getConfigurationChooser().setSelectedFile(null);
    }

    /**
     * Creates the UI components which need specific constructor parameters.
     */
    private void createUIComponents(){
        // Create sprite selection combobox.
        this.spriteSelectionComboBox = new JComboBox<>();

        // Create type combobox.
        this.typeComboBox = new JComboBox<>(Arrays
            .stream(EnumSpriteType.values())
            .map(EnumSpriteType::toLocalizedString)
            .toArray()
        );

        // Create subtype combobox.
        this.subtypeComboBox = new JComboBox<>(Arrays
            .stream(EnumSpriteSubType.values())
            .map(EnumSpriteSubType::toLocalizedString)
            .toArray()
        );

        // Create object clipping image.
        this.objectClippingImage = new JScaledImage();
        this.objectClippingImage.setBackground(new Color(0x54D880));

        // Create sprite clipping image.
        this.spriteClippingImage = new JScaledImage();
        this.spriteClippingImage.setBackground(new Color(0x73BDFF));

        // Create palette image.
        this.paletteView = new JPaletteView();
        this.paletteView
            .setPalette(this.getApplication().getPalette())
            .setCellSize(16, 16)
            .setFirstIndex(0x80)
            .setRegionSize(8, 1);
        this.paletteView.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    /**
     * Saves the current provider to the given file.
     * <p>
     * If a {@code null} {@code file} parameter is provided, the currently
     * opened file will be save in-place. If no file is currently open, a file
     * chooser will be shown to the user.
     *
     * @param file the file to save to
     */
    public void saveProvider(File file){
        Provider provider = this.getProvider();

        if(provider == null){
            this.getApplication().getNotifier().error(this, "chooser", "save.blank");
            return;
        }

        Provider replacement = null;

        try {
            if(file == null){
                File input = provider.getInput();

                if(input != null){
                    replacement = provider.save(input);
                }else{
                    this.showSaveDialog();
                    return;
                }
            }else{
                replacement = provider.save(file);
            }
        }catch(SavingException exception){
            this.getApplication().getExceptionHandler().report(exception);

            String message = exception.getMessage();

            if(exception.getCause() != null){
                message += " (" + exception.getCause().getClass().getName() + ")";
            }

            this.getApplication().getNotifier().error(this, "chooser", "save", "message", message);
        }

        if(replacement != null && this.unloadProvider()){
            this.setProvider(replacement);
        }
    }

    /**
     * Adds an action listener to a {@link JComboBox}.
     * <p>
     * The provided consumer is called with the selected index as an integer.
     *
     * @param comboBox the combo box to add a listener on
     * @param consumer the consumer to call when a value is selected
     */
    private void addComboBoxListener(JComboBox<?> comboBox, Consumer<Integer> consumer){
        comboBox.addActionListener(event -> {
            JComboBox<?> source = (JComboBox<?>)event.getSource();
            consumer.accept(source.getSelectedIndex());
        });
    }

    /**
     * Adds a key listener to a {@link JTextField}.
     * <p>
     * The provided consumer is called with the field's text every time the
     * text could have changed.
     *
     * @param field the field to add a listener to
     * @param consumer the consumer to call when a key is released
     */
    private void addTextFieldListener(JTextField field, Consumer<String> consumer){
        // yes i'm paranoid shut up
        field.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent event){
                JTextField source = (JTextField)event.getSource();
                consumer.accept(source.getText());
            }

            @Override
            public void keyPressed(KeyEvent event){
                JTextField source = (JTextField)event.getSource();
                consumer.accept(source.getText());
            }

            @Override
            public void keyReleased(KeyEvent event){
                JTextField source = (JTextField)event.getSource();
                consumer.accept(source.getText());
            }
        });
    }

    /**
     * Changes the "enabled" state of all user input elements.
     * <p>
     * This includes text fields, formatted text fields, checkboxes, and combo
     * boxes.
     *
     * @param enabled whether to enable or disable all input elements
     */
    private void setInputEnabled(boolean enabled){
        this.setInputEnabled(enabled, null);
    }

    /**
     * Changes the "enabled" state of all user input elements.
     * <p>
     * This includes text fields, formatted text fields, checkboxes, and combo
     * boxes.
     * <p>
     * If an {@link Annotation} is given, this method will
     * only affect the fields which are annotated with that {@code Annotation}.
     *
     * @param enabled whether to enable or disable all input elements
     * @param annotation the annotation class to check for
     */
    private void setInputEnabled(boolean enabled, Class<? extends Annotation> annotation){
        Field[] fields = this.getClass().getDeclaredFields();

        for(Field field : fields){
            Class<?> type = field.getType();

            if(type == JComboBox.class || type == JTextField.class || type == JFormattedTextField.class || type == JCheckBox.class){
                if(annotation != null && field.getAnnotation(annotation) == null){
                    continue;
                }

                try {
                    JComponent component = (JComponent)field.get(this);
                    component.setEnabled(enabled);
                }catch(IllegalAccessException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Loads the given sprite into the provider and updates all of the user
     * input elements to show its properties.
     *
     * @param index the sprite index to load
     * @return the window, for chaining
     * @throws NullPointerException if there is no active provider
     * @see Provider#loadSprite(int)
     */
    public MainWindow loadSprite(int index) throws ProvisionException{
        this.getProvider().loadSprite(index);
        this.updateGUI();

        Sprite sprite = this.getProvider().getCurrentSprite();
        SpriteBehavior behavior = sprite.getBehavior();

        this.spriteSelectionComboBox.setSelectedIndex(index);

        this.typeComboBox.setSelectedIndex(sprite.getType().ordinal());
        this.subtypeComboBox.setSelectedIndex(sprite.getSubType().ordinal());
        this.actsLikeTextField.setValue(sprite.getActsLike());

        this.firstASMTextField.setText(sprite.getFirstASMFile());
        this.secondASMTextField.setText(sprite.getSecondASMFile());

        // 3 am telinc, this is 4 pm telinc speaking
        // i found you a method of doing this which is both reflect-y and unorthodox
        // happy now? 4 pm telinc, out
        for(Field field : this.getClass().getDeclaredFields()){
            if(field.getType() != JCheckBox.class){
                continue;
            }

            BehaviorBit annotation = field.getDeclaredAnnotation(BehaviorBit.class);

            if(annotation == null){
                continue;
            }

            try {
                JCheckBox checkBox = (JCheckBox)field.get(this);
                Field bit = SpriteBehavior.class.getDeclaredField(annotation.value());
                checkBox.setSelected(bit.getBoolean(behavior));
            }catch(ReflectiveOperationException exception){
                exception.printStackTrace();
            }
        }

        this.setObjectClipping(behavior.objectClipping);
        this.setSpriteClipping(behavior.spriteClipping);
        this.setSpritePalette(behavior.palette);

        this.firstPropertyTextField.setValue(sprite.getFirstPropertyByte());
        this.secondPropertyTextField.setValue(sprite.getSecondPropertyByte());
        this.statusOverrideComboBox.setSelectedIndex(sprite.getStatusHandling().ordinal());
        this.uniqueByteTextField.setValue(sprite.getUniqueByte());
        this.extraByteAmountTextField.setValue(sprite.getExtraBytes());

        return this;
    }

    /**
     * Creates and loads a new {@link BlankProvider}.
     */
    public void createBlankFile(){
        if(!this.unloadProvider()){
            return;
        }

        BlankProvider provider = new BlankProvider();
        this.setProvider(provider);
    }

    /**
     * Shows an open dialog and opens the selected file through the appropriate
     * provider.
     */
    public void showOpenDialog(){
        if(!this.unloadProvider()){
            return;
        }

        int result = this.getConfigurationChooser().showOpen(this);

        if(result == JFileChooser.APPROVE_OPTION){
            File file = this.getConfigurationChooser().getSelectedFile();

            if(TypeUtils.isConfiguration(file)){
                ConfigurationProvider provider = new ConfigurationProvider(file);
                this.setProvider(provider);
            }else if(TypeUtils.isROM(file)){
                try {
                    ROMProvider provider = new ROMProvider(file);
                    this.setProvider(provider);
                }catch(LoadingException exception){
                    this.getApplication().getExceptionHandler().handle(this, exception);
                }
            }else{
                this.getApplication().getNotifier().error(this, "file", "load.type");
            }
        }

        this.getConfigurationChooser().setSelectedFile(null);
    }

    /**
     * Sets the object clipping of the active sprite and updates the fields
     * related to it.
     *
     * @param index the new object clipping
     */
    private void setObjectClipping(int index){
        index = Math.max(Math.min(index, 0xF), 0x0);

        if(this.getProvider() != null && this.getProvider().getCurrentSprite().getBehavior().objectClipping != index){
            this.modifyBehavior().objectClipping = (byte)index;
        }

        this.objectClippingComboBox.setSelectedIndex(index);

        try {
            this.objectClippingImage.loadImage(String.format("object/%02X.png", index));
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Sets the sprite clipping of the active sprite and updates the fields
     * related to it.
     *
     * @param index the new sprite clipping
     */
    private void setSpriteClipping(int index){
        index = Math.max(Math.min(index, 0x3F), 0x0);

        if(this.getProvider() != null && this.getProvider().getCurrentSprite().getBehavior().spriteClipping != index){
            this.modifyBehavior().spriteClipping = (byte)index;
        }

        this.spriteClippingComboBox.setSelectedIndex(index);

        try {
            this.spriteClippingImage.loadImage(String.format("sprite/%02X.png", index));
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Starts a modification on the current sprite's.
     *
     * @return the {@link Sprite} which can be modified
     * @throws NullPointerException if there is no active provider
     */
    private Sprite startModification(){
        return this.getProvider().startModification();
    }

    /**
     * Starts a modification on the current sprite's behavior.
     *
     * @return the {@link SpriteBehavior} which can be modified
     * @throws NullPointerException if there is no active provider
     */
    private SpriteBehavior modifyBehavior(){
        return this.getProvider().startModification().getBehavior();
    }

    /**
     * Returns the current sprite provider used by the window.
     *
     * @return a nullable {@link Provider}
     */
    public Provider getProvider(){
        return this.provider;
    }

    /**
     * Sets a new sprite provider for the window.
     * <p>
     * If the provider is set to a null value, the interface will be wholly
     * disabled and no file will be editable.
     * <p>
     * If the provider is non-null, the interface will be updated according to
     * its settings and the first sprite from it will be loaded.
     *
     * @param provider the new provider
     * @return the window, for chaining
     */
    public MainWindow setProvider(Provider provider){
        this.provider = provider;

        this.setTitle(Resources.getString("main", "title"));

        if(provider == null){
            this.setInputEnabled(false);
            return this;
        }

        String[] availableSprites = provider.getAvailableSprites();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(availableSprites);
        this.spriteSelectionComboBox.setModel(model);
        this.spriteSelectionComboBox.setSelectedIndex(0);

        try {
            this.loadSprite(0);
        }catch(ProvisionException exception){
            this.getApplication().getExceptionHandler().handle(this, exception);
            return this.setProvider(null);
        }

        if(provider.getInput() != null){
            this.setTitle(Resources.getString("main", "title.open", "path", provider.getInput().getAbsolutePath()));
        }else{
            this.setTitle(Resources.getString("main", "title"));
        }

        for(Warning warning : provider.getWarnings()){
            this.getApplication().getNotifier().notify(this, warning);
        }

        return this;
    }

    /**
     * Sets the palette of the active sprite and updates the fields related to
     * it.
     *
     * @param palette the new palette
     */
    private void setSpritePalette(int palette){
        palette = Math.max(Math.min(palette, 0x7), 0x0);

        if(this.getProvider() != null && this.getProvider().getCurrentSprite().getBehavior().palette != palette){
            this.modifyBehavior().palette = (byte)palette;
        }

        this.paletteComboBox.setSelectedIndex(palette);
        this.paletteView.setFirstIndex(0x80 + palette * 0x10);
    }

    /**
     * Updates the GUI based on the current sprite's available properties.
     *
     * @return the window, for chaining
     */
    public MainWindow updateGUI(){
        Provider provider = this.getProvider();
        this.setInputEnabled(false);

        if(provider == null){
            return this;
        }

        this.spriteSelectionComboBox.setEnabled(provider.getAvailableSprites().length > 1);

        Sprite sprite = provider.getCurrentSprite();
        EnumSpriteSubType subtype = sprite.getSubType();

        this.typeComboBox.setEnabled(!(provider instanceof ROMProvider));
        this.subtypeComboBox.setEnabled(!(provider instanceof ROMProvider));

        if(sprite.hasActsLike() && !(provider instanceof ROMProvider)){
            this.actsLikeTextField.setEnabled(true);
        }

        if(sprite.hasBehavior()){
            this.setInputEnabled(true, BehaviorBit.class);
        }

        if(sprite.hasPropertyBytes()){
            this.firstPropertyTextField.setEnabled(true);
            this.secondPropertyTextField.setEnabled(true);
            this.statusOverrideComboBox.setEnabled(true);
        }

        if(sprite.hasUniqueByte()){
            this.uniqueByteTextField.setEnabled(true);
        }

        if(sprite.getMaxExtraBytes() > 0){
            this.extraByteAmountTextField.setEnabled(true);
        }

        if(sprite.usesFirstASM()){
            this.firstASMTextField.setEnabled(true);
        }

        if(sprite.usesSecondASM()){
            this.secondASMTextField.setEnabled(true);
        }

        JFormattedTextField.AbstractFormatter formatter = this.extraByteAmountTextField.getFormatter();

        if(formatter instanceof DecimalFormatter){
            ((DecimalFormatter)formatter).setBounds(0, sprite.getSubType().getMaxExtraBytes());
        }

        return this;
    }

    /**
     * Returns the menu bar for the application window.
     *
     * @return an instance of {@link MenuBar} which represents the
     * menu bar
     */
    public MenuBar getMenu(){
        return this.menuBar;
    }

    /**
     * Returns the parent application of the window.
     *
     * @return the {@code Application} hosting the window
     */
    public Application getApplication(){
        return this.application;
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$(){
        createUIComponents();
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, 0));
        panel1.setEnabled(true);
        panel1.setFocusable(true);
        panel1.setOpaque(true);
        panel1.setVisible(true);
        contentPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.spriteSelection")));
        panel1.add(spriteSelectionComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        contentPanel.add(tabbedPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        spriteScrollPane = new JScrollPane();
        spriteScrollPane.setEnabled(true);
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.tab.sprite"), spriteScrollPane);
        spritePanel = new JPanel();
        spritePanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        spriteScrollPane.setViewportView(spritePanel);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(6, 2, new Insets(0, 0, 0, 0), -1, 0));
        panel2.setEnabled(true);
        spritePanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1656"));
        canBeJumpedOnCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(canBeJumpedOnCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.canBeJumpedOn"));
        panel2.add(canBeJumpedOnCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        diesWhenJumpedOnCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(diesWhenJumpedOnCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.diesWhenJumpedOn"));
        panel2.add(diesWhenJumpedOnCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        hopInKickShellsCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(hopInKickShellsCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.hopInShells"));
        panel2.add(hopInKickShellsCheckBox, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        disappearInACloudCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disappearInACloudCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disappearInSmoke"));
        panel2.add(disappearInACloudCheckBox, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.objectClipping"));
        panel2.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        objectClippingComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("00");
        defaultComboBoxModel1.addElement("01");
        defaultComboBoxModel1.addElement("02");
        defaultComboBoxModel1.addElement("03");
        defaultComboBoxModel1.addElement("04");
        defaultComboBoxModel1.addElement("05");
        defaultComboBoxModel1.addElement("06");
        defaultComboBoxModel1.addElement("07");
        defaultComboBoxModel1.addElement("08");
        defaultComboBoxModel1.addElement("09");
        defaultComboBoxModel1.addElement("0A");
        defaultComboBoxModel1.addElement("0B");
        defaultComboBoxModel1.addElement("0C");
        defaultComboBoxModel1.addElement("0D");
        defaultComboBoxModel1.addElement("0E");
        defaultComboBoxModel1.addElement("0F");
        objectClippingComboBox.setModel(defaultComboBoxModel1);
        panel2.add(objectClippingComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.visualizedObjectClipping")));
        objectClippingImage.setFocusable(false);
        objectClippingImage.setOpaque(false);
        panel3.add(objectClippingImage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(84, 84), null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1662"));
        useShellAsDeathCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(useShellAsDeathCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.shellAsDeathFrame"));
        panel4.add(useShellAsDeathCheckBox, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        fallsWhenKilledCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(fallsWhenKilledCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.fallsWhenKilled"));
        panel4.add(fallsWhenKilledCheckBox, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        this.$$$loadLabelText$$$(label2, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.spriteClipping"));
        panel4.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spriteClippingComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("00");
        defaultComboBoxModel2.addElement("01");
        defaultComboBoxModel2.addElement("02");
        defaultComboBoxModel2.addElement("03");
        defaultComboBoxModel2.addElement("04");
        defaultComboBoxModel2.addElement("05");
        defaultComboBoxModel2.addElement("06");
        defaultComboBoxModel2.addElement("07");
        defaultComboBoxModel2.addElement("08");
        defaultComboBoxModel2.addElement("09");
        defaultComboBoxModel2.addElement("0A");
        defaultComboBoxModel2.addElement("0B");
        defaultComboBoxModel2.addElement("0C");
        defaultComboBoxModel2.addElement("0D");
        defaultComboBoxModel2.addElement("0E");
        defaultComboBoxModel2.addElement("0F");
        defaultComboBoxModel2.addElement("10");
        defaultComboBoxModel2.addElement("11");
        defaultComboBoxModel2.addElement("12");
        defaultComboBoxModel2.addElement("13");
        defaultComboBoxModel2.addElement("14");
        defaultComboBoxModel2.addElement("15");
        defaultComboBoxModel2.addElement("16");
        defaultComboBoxModel2.addElement("17");
        defaultComboBoxModel2.addElement("18");
        defaultComboBoxModel2.addElement("19");
        defaultComboBoxModel2.addElement("1A");
        defaultComboBoxModel2.addElement("1B");
        defaultComboBoxModel2.addElement("1C");
        defaultComboBoxModel2.addElement("1D");
        defaultComboBoxModel2.addElement("1E");
        defaultComboBoxModel2.addElement("1F");
        defaultComboBoxModel2.addElement("20");
        defaultComboBoxModel2.addElement("21");
        defaultComboBoxModel2.addElement("22");
        defaultComboBoxModel2.addElement("23");
        defaultComboBoxModel2.addElement("24");
        defaultComboBoxModel2.addElement("25");
        defaultComboBoxModel2.addElement("26");
        defaultComboBoxModel2.addElement("27");
        defaultComboBoxModel2.addElement("28");
        defaultComboBoxModel2.addElement("29");
        defaultComboBoxModel2.addElement("2A");
        defaultComboBoxModel2.addElement("2B");
        defaultComboBoxModel2.addElement("2C");
        defaultComboBoxModel2.addElement("2D");
        defaultComboBoxModel2.addElement("2E");
        defaultComboBoxModel2.addElement("2F");
        defaultComboBoxModel2.addElement("30");
        defaultComboBoxModel2.addElement("31");
        defaultComboBoxModel2.addElement("32");
        defaultComboBoxModel2.addElement("33");
        defaultComboBoxModel2.addElement("34");
        defaultComboBoxModel2.addElement("35");
        defaultComboBoxModel2.addElement("36");
        defaultComboBoxModel2.addElement("37");
        defaultComboBoxModel2.addElement("38");
        defaultComboBoxModel2.addElement("39");
        defaultComboBoxModel2.addElement("3A");
        defaultComboBoxModel2.addElement("3B");
        defaultComboBoxModel2.addElement("3C");
        defaultComboBoxModel2.addElement("3D");
        defaultComboBoxModel2.addElement("3E");
        defaultComboBoxModel2.addElement("3F");
        spriteClippingComboBox.setModel(defaultComboBoxModel2);
        panel4.add(spriteClippingComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.visualizedSpriteClipping")));
        spriteClippingImage.setFocusable(false);
        spriteClippingImage.setOpaque(false);
        panel5.add(spriteClippingImage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(84, 84), null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(6, 3, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel6.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$166E"));
        useSecondGraphicsPageCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(useSecondGraphicsPageCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.useSecondGraphicsPage"));
        panel6.add(useSecondGraphicsPageCheckBox, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        this.$$$loadLabelText$$$(label3, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.palette"));
        panel6.add(label3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paletteComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("8");
        defaultComboBoxModel3.addElement("9");
        defaultComboBoxModel3.addElement("A");
        defaultComboBoxModel3.addElement("B");
        defaultComboBoxModel3.addElement("C");
        defaultComboBoxModel3.addElement("D");
        defaultComboBoxModel3.addElement("E");
        defaultComboBoxModel3.addElement("F");
        paletteComboBox.setModel(defaultComboBoxModel3);
        panel6.add(paletteComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        disableFireballKillingCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableFireballKillingCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableFireballKilling"));
        panel6.add(disableFireballKillingCheckBox, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableCapeKillingCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableCapeKillingCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableCapeKilling"));
        panel6.add(disableCapeKillingCheckBox, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableWaterSplashCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableWaterSplashCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableWaterSplash"));
        panel6.add(disableWaterSplashCheckBox, new GridConstraints(4, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        disableSecondaryInteractionCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(disableSecondaryInteractionCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableSecondaryInteraction"));
        panel6.add(disableSecondaryInteractionCheckBox, new GridConstraints(5, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        paletteView.setFocusable(false);
        panel6.add(paletteView, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(128, 16), null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel7, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel7.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$167A"));
        executeWhileBeingKilledCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(executeWhileBeingKilledCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.processIfDead"));
        panel7.add(executeWhileBeingKilledCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        invincibleToPlayerCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(invincibleToPlayerCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.invincibleToPlayer"));
        panel7.add(invincibleToPlayerCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        executeIfOffscreenCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(executeIfOffscreenCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.executeIfOffscreen"));
        panel7.add(executeIfOffscreenCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTChangeIntoCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTChangeIntoCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.skipShellWhenStunned"));
        panel7.add(donTChangeIntoCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canTBeKickedCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(canTBeKickedCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableKicking"));
        panel7.add(canTBeKickedCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        processInteractionEveryFrameCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(processInteractionEveryFrameCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.processInteractionEveryFrame"));
        panel7.add(processInteractionEveryFrameCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        givesPowerupWhenEatenCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(givesPowerupWhenEatenCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.isPowerup"));
        panel7.add(givesPowerupWhenEatenCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTUseDefaultCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTUseDefaultCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableDefaultInteraction"));
        panel7.add(donTUseDefaultCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel8, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel8.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$1686"));
        inedibleCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(inedibleCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.inedible"));
        panel8.add(inedibleCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stayInYoshiSCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(stayInYoshiSCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.stayInMouth"));
        panel8.add(stayInYoshiSCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        weirdGroundBehaviorCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(weirdGroundBehaviorCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.weirdGroundBehavior"));
        panel8.add(weirdGroundBehaviorCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTInteractWithCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableSpriteInteraction"));
        panel8.add(donTInteractWithCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTChangeDirectionCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTChangeDirectionCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.preserveDirection"));
        panel8.add(donTChangeDirectionCheckBox, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTTurnIntoCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTTurnIntoCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disappearWhenGoalPassed"));
        panel8.add(donTTurnIntoCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spawnsANewSpriteCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(spawnsANewSpriteCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.spawnsNewSprite"));
        panel8.add(spawnsANewSpriteCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTInteractWithCheckBox1 = new JCheckBox();
        this.$$$loadButtonText$$$(donTInteractWithCheckBox1, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableObjectInteraction"));
        panel8.add(donTInteractWithCheckBox1, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayoutManager(8, 1, new Insets(0, 0, 0, 0), -1, 0));
        spritePanel.add(panel9, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel9.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "$190F"));
        makePlatformPassableFromCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(makePlatformPassableFromCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.platformPassableFromBelow"));
        panel9.add(makePlatformPassableFromCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTEraseWhenCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTEraseWhenCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.ignoreGoal"));
        panel9.add(donTEraseWhenCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canTBeKilledCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(canTBeKilledCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.disableSlideKilling"));
        panel9.add(canTBeKilledCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        takes5FireballsToCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(takes5FireballsToCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.takesFiveFireballs"));
        panel9.add(takes5FireballsToCheckBox, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        canBeJumpedOnCheckBox1 = new JCheckBox();
        this.$$$loadButtonText$$$(canBeJumpedOnCheckBox1, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.canBeJumpedOnFromBelow"));
        panel9.add(canBeJumpedOnCheckBox1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deathFrame2TilesCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(deathFrame2TilesCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.tallDeathFrame"));
        panel9.add(deathFrame2TilesCheckBox, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ignoreSilverPSwitchCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(ignoreSilverPSwitchCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.ignoreSilverPSwitch"));
        panel9.add(ignoreSilverPSwitchCheckBox, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        donTGetStuckCheckBox = new JCheckBox();
        this.$$$loadButtonText$$$(donTGetStuckCheckBox, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.behavior.ignoreWalls"));
        panel9.add(donTGetStuckCheckBox, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel10, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, 1, 1, null, null, null, 0, false));
        panel10.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.insertion.title")));
        panel10.add(typeComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel10.add(subtypeComboBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        this.$$$loadLabelText$$$(label4, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.insertion.asm"));
        panel10.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstASMTextField = new JTextField();
        firstASMTextField.setText("");
        panel10.add(firstASMTextField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        secondASMTextField = new JTextField();
        panel10.add(secondASMTextField, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        this.$$$loadLabelText$$$(label5, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.insertion.actsLike"));
        panel10.add(label5, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        actsLikeTextField = new JFormattedTextField();
        panel10.add(actsLikeTextField, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        spritePanel.add(panel11, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel11.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.title")));
        final JLabel label6 = new JLabel();
        this.$$$loadLabelText$$$(label6, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.propertyBytes"));
        panel11.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstPropertyTextField = new JFormattedTextField();
        panel11.add(firstPropertyTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        secondPropertyTextField = new JFormattedTextField();
        panel11.add(secondPropertyTextField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        this.$$$loadLabelText$$$(label7, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.statusOverride"));
        panel11.add(label7, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        statusOverrideComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        defaultComboBoxModel4.addElement("Handle stunned");
        defaultComboBoxModel4.addElement("Handle all");
        defaultComboBoxModel4.addElement("Override all");
        statusOverrideComboBox.setModel(defaultComboBoxModel4);
        panel11.add(statusOverrideComboBox, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        this.$$$loadLabelText$$$(label8, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.uniqueByte"));
        panel11.add(label8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        uniqueByteTextField = new JFormattedTextField();
        panel11.add(uniqueByteTextField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        this.$$$loadLabelText$$$(label9, ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("sprite.data.extraBytes"));
        panel11.add(label9, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        extraByteAmountTextField = new JFormattedTextField();
        panel11.add(extraByteAmountTextField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        displayPanel = new JPanel();
        displayPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab(ResourceBundle.getBundle("com/telinc1/faerie/locale/Main").getString("content.tab.display"), displayPanel);
        final JLabel label10 = new JLabel();
        label10.setText("This function is not yet implemented.");
        displayPanel.add(label10, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        label1.setLabelFor(objectClippingComboBox);
        label2.setLabelFor(spriteClippingComboBox);
        label3.setLabelFor(paletteComboBox);
        label4.setLabelFor(firstASMTextField);
        label5.setLabelFor(actsLikeTextField);
        label6.setLabelFor(firstPropertyTextField);
        label7.setLabelFor(statusOverrideComboBox);
        label8.setLabelFor(uniqueByteTextField);
        label9.setLabelFor(extraByteAmountTextField);
    }

    /** @noinspection ALL */
    private void $$$loadLabelText$$$(JLabel component, String text){
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '&'){
                i++;
                if(i == text.length()) break;
                if(!haveMnemonic && text.charAt(i) != '&'){
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if(haveMnemonic){
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /** @noinspection ALL */
    private void $$$loadButtonText$$$(AbstractButton component, String text){
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for(int i = 0; i < text.length(); i++){
            if(text.charAt(i) == '&'){
                i++;
                if(i == text.length()) break;
                if(!haveMnemonic && text.charAt(i) != '&'){
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if(haveMnemonic){
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /** @noinspection ALL */
    public JComponent $$$getRootComponent$$$(){ return contentPanel; }
}
