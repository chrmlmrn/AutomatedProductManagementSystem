package admin.product.barcode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class BarcodeGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int fileCount = 1; // Initialize a counter for the filenames

        while (true) {
            System.out.print("Enter the product barcode (EAN-13 or EAN-14): ");
            String barcodeData = scanner.nextLine();

            // Determine the barcode type based on length
            if (barcodeData.length() != 13 && barcodeData.length() != 14) {
                System.out.println("Error: Barcode number must be exactly 13 or 14 digits.");
                continue; // Prompt the user to enter the barcode again
            }

            // Ensure the input contains only digits
            if (!barcodeData.matches("\\d+")) {
                System.out.println("Error: Barcode number must contain only digits.");
                continue; // Prompt the user to enter the barcode again
            }

            String barcodeFilePath = "C:\\Users\\ADMIN\\Documents\\SampleBarcode\\barcode_" + fileCount + ".png";
            fileCount++; // Increment the counter for the next file name

            if (barcodeData.length() == 13) {
                generateEAN13Barcode(barcodeData, barcodeFilePath);
                System.out.println("Generated EAN-13 Barcode as image: " + barcodeFilePath);
            } else if (barcodeData.length() == 14) {
                generateEAN14Barcode(barcodeData, barcodeFilePath);
                System.out.println("Generated EAN-14 Barcode as image: " + barcodeFilePath);
            }

            System.out.print("Do you want to generate another barcode? (yes/no): ");
            String continueResponse = scanner.nextLine();
            if (!continueResponse.equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
    }

    public static BufferedImage generateEAN13Barcode(String data, String filePath) {
        // Define dimensions based on provided specifications
        double barcodeWidthMM = 31.35;
        double leftQuietZoneMM = 3.63;
        double rightQuietZoneMM = 2.31;
        double barcodeHeightMM = 22.85;
        double numberHeightMM = 7.0; // Height of the number below the barcode

        // Convert dimensions to pixels (assuming 1mm = 3.7795275590551 pixels)
        int width = (int) (barcodeWidthMM * 3.7795275590551);
        int height = (int) (barcodeHeightMM * 3.7795275590551);
        int numberHeight = (int) (numberHeightMM * 3.7795275590551);
        int leftQuietZone = (int) (leftQuietZoneMM * 3.7795275590551);
        int rightQuietZone = (int) (rightQuietZoneMM * 3.7795275590551);

        // Create BufferedImage
        BufferedImage image = new BufferedImage(width + leftQuietZone + rightQuietZone, height + numberHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width + leftQuietZone + rightQuietZone, height + numberHeight);

        // Draw barcode
        g2d.setColor(Color.BLACK);
        int x = 20;
        int y = 20;
        String binaryRepresentation = getBinaryRepresentation(data);
        for (int i = 0; i < binaryRepresentation.length(); i++) {
            char bit = binaryRepresentation.charAt(i);
            if (bit == '1') {
                int lineHeight = (i < 3 || (i >= 45 && i < 50) || i >= 92) ? height - 1 : height - 10;
                g2d.fillRect(x, y, 1, lineHeight);
            }
            x++;
        }

        // Draw barcode number with adjusted spacing
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int textY = height + 20; // Position the number below the barcode with proper spacing

        // Draw EAN-13 formatted number
        drawEAN13Number(g2d, fm, data, width, leftQuietZone, rightQuietZone, textY);

        g2d.dispose();

        // Save image to file
        try {
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static BufferedImage generateEAN14Barcode(String data, String filePath) {
        // Define dimensions based on provided specifications
        double barcodeWidthMM = 41.35; // Increased width to accommodate EAN-14
        double leftQuietZoneMM = 10.0; // Increased left quiet zone for better scanning
        double rightQuietZoneMM = 10.0; // Increased right quiet zone for better scanning
        double barcodeHeightMM = 22.85;
        double numberHeightMM = 7.0; // Height of the number below the barcode

        // Convert dimensions to pixels (assuming 1mm = 3.7795275590551 pixels)
        int width = (int) (barcodeWidthMM * 3.7795275590551);
        int height = (int) (barcodeHeightMM * 3.7795275590551);
        int numberHeight = (int) (numberHeightMM * 3.7795275590551);
        int leftQuietZone = (int) (leftQuietZoneMM * 3.7795275590551);
        int rightQuietZone = (int) (rightQuietZoneMM * 3.7795275590551);

        // Create BufferedImage
        BufferedImage image = new BufferedImage(width + leftQuietZone + rightQuietZone, height + numberHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width + leftQuietZone + rightQuietZone, height + numberHeight);

        // Draw barcode
        g2d.setColor(Color.BLACK);
        int x = 20; // Start drawing from the leftQuietZone
        int y = 20; // Adjust this value to position the barcode vertically

        // Get binary representation of the data using Code 128 encoding
        String binaryRepresentation = getBinaryRepresentationCode128(data);

        // Draw each bit of the barcode
        for (int i = 0; i < binaryRepresentation.length(); i++) {
            char bit = binaryRepresentation.charAt(i);
            if (bit == '1') {
                int lineHeight = height - 10;
                g2d.fillRect(x, y, 1, lineHeight);
            }
            x++;
        }

        // Draw barcode number with adjusted spacing
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g2d.getFontMetrics();
        int textY = height + 20; // Position the number below the barcode with proper spacing

        // Draw EAN-14 formatted number
        drawEAN14Number(g2d, fm, data, width, leftQuietZone, rightQuietZone, textY);

        g2d.dispose();

        // Save image to file
        try {
            File file = new File(filePath);
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    // Method to convert the data into its binary representation
    // Method to convert the data into its binary representation
    private static String getBinaryRepresentation(String data) {
        StringBuilder binaryBuilder = new StringBuilder();
        binaryBuilder.append("101"); // Start pattern

        // Encoding schemes for the left side of the barcode
        String[] leftEncoding = {
                "0001101", "0011001", "0010011", "0111101", "0100011",
                "0110001", "0101111", "0111011", "0110111", "0001011"
        };

        String[] leftEncodingG = {
                "0100111", "0110011", "0011011", "0100001", "0011101",
                "0111001", "0000101", "0010001", "0001001", "0010111"
        };

        String[] rightEncoding = {
                "1110010", "1100110", "1101100", "1000010", "1011100",
                "1001110", "1010000", "1000100", "1001000", "1110100"
        };

        // Patterns to select which encoding to use for the left side digits
        String[] encodingPattern = {
                "LLLLLL", "LLGLGG", "LLGGLG", "LLGGGL", "LGLLGG",
                "LGGLLG", "LGGGLL", "LGLGLG", "LGLGGL", "LGGLGL"
        };

        int firstDigit = Character.getNumericValue(data.charAt(0));
        String pattern = encodingPattern[firstDigit];

        // Encode the first six digits according to the pattern
        for (int i = 1; i <= 6; i++) {
            int digit = Character.getNumericValue(data.charAt(i));
            if (pattern.charAt(i - 1) == 'L') {
                binaryBuilder.append(leftEncoding[digit]);
            } else {
                binaryBuilder.append(leftEncodingG[digit]);
            }
        }

        // Middle pattern
        binaryBuilder.append("01010");

        // Encode the last six digits
        for (int i = 7; i <= 12; i++) {
            int digit = Character.getNumericValue(data.charAt(i));
            binaryBuilder.append(rightEncoding[digit]);
        }

        binaryBuilder.append("101"); // End pattern
        return binaryBuilder.toString();
    }

    // Method to get Code 128 binary representation of the data
    private static String getBinaryRepresentationCode128(String data) {
        StringBuilder binaryBuilder = new StringBuilder();

        // Start code
        binaryBuilder.append("11010010000");

        // Encode data using Code 128 encoding
        for (int i = 0; i < data.length(); i++) {
            int charValue = data.charAt(i);
            if (charValue >= 32 && charValue <= 126) {
                binaryBuilder.append(Code128[charValue - 32]);
            } else {
                throw new IllegalArgumentException("Invalid character in input for Code 128: " + data.charAt(i));
            }
        }

        // Calculate checksum using Code 128
        int checksum = 104; // Start character
        for (int i = 0; i < data.length(); i++) {
            int charValue = data.charAt(i);
            checksum += (charValue - 32) * (i + 1);
        }
        binaryBuilder.append(Code128[checksum % 103]);

        // Stop code
        binaryBuilder.append("1100011101011");

        return binaryBuilder.toString();
    }

    // Code 128 character encoding table
    private static final String[] Code128 = {
            "11011001100", "11001101100", "11001100110", "10010011000", "10010001100",
            "10001001100", "10011001000", "10011000100", "10001100100", "11001001000",
            "11001000100", "11000100100", "10110011100", "10011011100", "10011001110",
            "10111001100", "10011101100", "10011100110", "11001110010", "11001011100",
            "11001001110", "11011100100", "11001110100", "11101101110", "11101001100",
            "11100101100", "11100100110", "11101100100", "11100110100", "11100110010",
            "11011011000", "11011000110", "11000110110", "10100011000", "10001011000",
            "10001000110", "10110001000", "10001101000", "10001100010", "11010001000",
            "11000101000", "11000100010", "10110111000", "10110001110", "10001101110",
            "10111011000", "10111000110", "10001110110", "11101110110", "11010001110",
            "11000101110", "11011101000", "11011100010", "11011101110", "11101011000",
            "11101000110", "11100010110", "11101101000", "11101100010", "11100011010",
            "11101111010", "11001000010", "11110001010", "10100110000", "10100001100",
            "10010110000", "10010000110", "10000101100", "10000100110", "10110010000",
            "10110000100", "10011010000", "10011000010", "10000110100", "10000110010",
            "11000010010", "11001010000", "11110111010", "11000010100", "10001111010",
            "10100111100", "10010111100", "10010011110", "10111100100", "10011110100",
            "10011110010", "11110100100", "11110010100", "11110010010", "11011011110",
            "11011110110", "11110110110", "10101111000", "10100011110", "10001011110",
            "10111101000", "10111100010", "11110101000", "11110100010", "10111011110",
            "10111101110", "11101011110", "11110101110", "11010000100", "11010010000",
            "11010011100", "1100011101011" // Stop code
    };

    // Method to draw EAN-13 formatted number below the barcode
    private static void drawEAN13Number(Graphics2D g2d, FontMetrics fm, String data, int barcodeWidth,
            int leftQuietZone, int rightQuietZone, int textY) {
        int startX = leftQuietZone - fm.stringWidth(data.charAt(0) + "");

        // Draw number system digit
        g2d.drawString(data.charAt(0) + "", startX, textY);

        // Draw first group of six digits
        startX += fm.stringWidth(data.charAt(0) + "") + 14;
        String firstGroup = data.substring(1, 7);
        g2d.drawString(firstGroup, startX, textY);

        // Draw second group of six digits
        startX += fm.stringWidth(firstGroup) + 10;
        String secondGroup = data.substring(7, 13);
        g2d.drawString(secondGroup, startX, textY);
    }

    // Method to draw EAN-14 formatted number below the barcode
    private static void drawEAN14Number(Graphics2D g2d, FontMetrics fm, String data, int barcodeWidth,
            int leftQuietZone, int rightQuietZone, int textY) {
        int startX = leftQuietZone;

        // Calculate the startX to center the number below the barcode
        startX += (barcodeWidth - fm.stringWidth(data)) / 2;

        // Draw all 14 digits in a continuous line
        g2d.drawString(data, startX, textY);
    }

}
