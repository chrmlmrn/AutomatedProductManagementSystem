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
            if (barcodeData.length() != 12 && barcodeData.length() != 13) {
                System.out.println("Error: Barcode number must be exactly 12 or 13 digits.");
                continue; // Prompt the user to enter the barcode again
            }

            // Ensure the input contains only digits
            if (!barcodeData.matches("\\d+")) {
                System.out.println("Error: Barcode number must contain only digits.");
                continue; // Prompt the user to enter the barcode again
            }

            // Calculate the last digit (checksum) if needed
            String fullBarcodeData;
            if (barcodeData.length() == 12) {
                char checksum = calculateChecksum(barcodeData);
                fullBarcodeData = barcodeData + checksum;
            } else {
                fullBarcodeData = barcodeData;
            }

            String barcodeFilePath = "C:\\Users\\ADMIN\\Documents\\SampleBarcode\\barcode_" + fileCount + ".png";
            fileCount++; // Increment the counter for the next file name

            if (fullBarcodeData.length() == 13) {
                generateEAN13Barcode(fullBarcodeData, barcodeFilePath);
                System.out.println("Generated EAN-13 Barcode as image: " + barcodeFilePath);
            } else {
                generateEAN14Barcode(fullBarcodeData, barcodeFilePath);
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

    public static char calculateChecksum(String data) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(data.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return (char) (checksum + '0');
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
        String binaryRepresentation = getBinaryRepresentationEAN14(data);
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

    // Method to convert the EAN-13 data into its binary representation
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

    // Method to convert the EAN-14 data into its binary representation
    private static String getBinaryRepresentationEAN14(String data) {
        StringBuilder binaryBuilder = new StringBuilder();
        binaryBuilder.append("1010"); // Start pattern for EAN-14

        // Encoding schemes for the EAN-14 barcode (using Code 128)
        // Code 128 encoding tables (just a sample, needs a full table for real use)
        String[] encoding = {
                "11011001100", "11001101100", "11001100110", "10010011000",
                "10010001100", "10001001100", "10011001000", "10011000100",
                "10001100100", "11001001000", "11001000100", "11000100100"
        };

        // Encode all digits
        for (int i = 0; i < 14; i++) {
            int digit = Character.getNumericValue(data.charAt(i));
            binaryBuilder.append(encoding[digit]);
        }

        binaryBuilder.append("1010"); // End pattern for EAN-14
        return binaryBuilder.toString();
    }

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

        // Draw all 14 digits in a continuous line
        g2d.drawString(data, startX, textY);
    }
}
