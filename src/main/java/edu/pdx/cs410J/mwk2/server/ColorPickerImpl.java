package edu.pdx.cs410J.mwk2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.mwk2.client.ColorPicker;
import edu.pdx.cs410J.mwk2.client.Colors;
import edu.pdx.cs410J.mwk2.client.ValidateInputException;

import java.awt.*;
import java.lang.Override;

/**
 * The server-side implementation of the Phone Bill service
 */
public class ColorPickerImpl extends RemoteServiceServlet implements ColorPicker {

  private String rgbColor;
  private String hexColor;
  private String colorName;
  private String colors;

  /**
   * Returns validated hex format color. Throws exception if input is not a valid hex color.
   * @param hex
   * @return
   * @throws ValidateInputException
   */
  @Override
  public String setHexColor(String hex) throws ValidateInputException {
    if (hex == null || "".equals(hex)) {
      throw new ValidateInputException("Input cannot be blank");
    } else

    if(!hex.matches("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")) {
      throw new ValidateInputException("Invalid hex color");
    }
    hexColor = hex;
    hexToRGB(hexColor);

    colorName = Colors.getHexToColorNameMap().get(hexColor);

    colors = setColorsString(colorName, hexColor, rgbColor);

    return colors;
  }

  @Override
  public String setNameColor(String name) {
    colorName = name;
    hexColor = Colors.getColorNamesToHexMap().get(colorName);
    hexToRGB(hexColor);
    colors = setColorsString(colorName, hexColor, rgbColor);

    return colors;
  }

  private String setColorsString(String colorName, String hexColor, String rgbColor) {

    StringBuilder sbColors = new StringBuilder();
    if (colorName != null) {
      sbColors.append(colorName.toUpperCase() + " ");
    }
    sbColors.append("HEX(" + hexColor + ") ");
    sbColors.append("RGB(" + rgbColor + ")");

    return sbColors.toString();
  }

  private void hexToRGB(String color) {
    int r = Color.decode(color).getRed();
    int g = Color.decode(color).getGreen();
    int b = Color.decode(color).getBlue();

    rgbColor = Integer.toString(r) + "," + Integer.toString(g) + "," + Integer.toString(b);
    //hexColor = String.format("#%02x%02x%02x", r, g, b);
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn"t handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
