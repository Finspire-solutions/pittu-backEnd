package com.finspire.Grandpittu.service.user;

import com.finspire.Grandpittu.entity.MenuItem;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PDFService {

    private static final BaseColor DARK_TEXT = BaseColor.BLACK;
    private static final BaseColor GRAY_TEXT = new BaseColor(80, 80, 80);
    private static final BaseColor ACCENT_COLOR = new BaseColor(51, 51, 51);

    public byte[] generateGrandPittuMenuPDF(List<MenuItem> menuItems) throws DocumentException {
        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Add content sections
            addHeader(document);
            addJaffnaMasalaSection(document);

            // Add menu sections based on categories
            addStarterSection(document, menuItems);
            addSignaturePittuSection(document, menuItems);
            addCurrySection(document, menuItems);

            // Add footer
            addFooter(document);

        } finally {
            document.close();
        }

        return out.toByteArray();
    }

    private void addHeader(Document document) throws DocumentException {
        // Main title
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 32, Font.BOLD, DARK_TEXT);
        Paragraph title = new Paragraph("GRAND PITTU", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(5);
        document.add(title);

        // Subtitle
        Font subtitleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, GRAY_TEXT);
        Paragraph subtitle = new Paragraph("A TASTE OF AUTHENTIC JAFFNA", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        subtitle.setSpacingAfter(30);
        document.add(subtitle);

        // Separator line
        addSeparatorLine(document);
    }

    private void addJaffnaMasalaSection(Document document) throws DocumentException {
        // Section title
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, DARK_TEXT);
        Paragraph sectionTitle = new Paragraph("OUR OWN HOMEMADE", sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        sectionTitle.setSpacingBefore(20);
        document.add(sectionTitle);

        Paragraph sectionTitle2 = new Paragraph("JAFFNA MASALA", sectionFont);
        sectionTitle2.setAlignment(Element.ALIGN_CENTER);
        sectionTitle2.setSpacingAfter(15);
        document.add(sectionTitle2);

        addSeparatorLine(document);

        // Description
        Font descFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, DARK_TEXT);
        Paragraph description = new Paragraph(
                "A wholesome spice blend made with 100% natural ingredients. " +
                        "No artificial flavours, no colourings, no preservatives. Just pure, " +
                        "freshly roasted spices that bring you the true taste of Jaffna " +
                        "healthy, aromatic, and authentic.", descFont);
        description.setAlignment(Element.ALIGN_JUSTIFIED);
        description.setSpacingAfter(30);
        description.setIndentationLeft(50);
        description.setIndentationRight(50);
        document.add(description);

        // Additional description
        Paragraph description2 = new Paragraph(
                "Bringing the wholesome flavors of Jaffna villages to your plate. " +
                        "Made with fresh, natural ingredients and traditional recipes, our " +
                        "dishes are light, nutritious, and full of authentic Northern " +
                        "Sri Lankan taste.", descFont);
        description2.setAlignment(Element.ALIGN_JUSTIFIED);
        description2.setSpacingAfter(30);
        description2.setIndentationLeft(50);
        description2.setIndentationRight(50);
        document.add(description2);
    }

    private void addStarterSection(Document document, List<MenuItem> menuItems) throws DocumentException {
        List<MenuItem> starters = menuItems.stream()
                .filter(item -> "starter".equalsIgnoreCase(item.getCategory()) && item.getAvailable())
                .collect(Collectors.toList());

        if (starters.isEmpty()) return;

        // Starter section title with border
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, DARK_TEXT);

        // Create bordered title
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidthPercentage(60);
        titleTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleTable.setSpacingBefore(30);
        titleTable.setSpacingAfter(30);

        PdfPCell titleCell = new PdfPCell(new Phrase("STARTER", sectionFont));
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setPadding(15);
        titleCell.setBorderWidth(2);
        titleCell.setBorderColor(DARK_TEXT);
        titleTable.addCell(titleCell);

        document.add(titleTable);

        // Add starter items
        for (MenuItem item : starters) {
            addSimpleMenuItem(document, item);
        }
    }

    private void addSignaturePittuSection(Document document, List<MenuItem> menuItems) throws DocumentException {
        List<MenuItem> pittus = menuItems.stream()
                .filter(item -> "pittu".equalsIgnoreCase(item.getCategory()) && item.getAvailable())
                .collect(Collectors.toList());

        if (pittus.isEmpty()) return;

        // Section title
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, DARK_TEXT);
        Paragraph sectionTitle = new Paragraph("SIGNATURE PITTU VARIETIES", sectionFont);
        sectionTitle.setAlignment(Element.ALIGN_CENTER);
        sectionTitle.setSpacingBefore(40);
        sectionTitle.setSpacingAfter(20);
        document.add(sectionTitle);

        addSeparatorLine(document);

        // Add each pittu variety with detailed description
        for (MenuItem item : pittus) {
            addDetailedPittuItem(document, item);
        }
    }

    private void addCurrySection(Document document, List<MenuItem> menuItems) throws DocumentException {
        List<MenuItem> curries = menuItems.stream()
                .filter(item -> "curry".equalsIgnoreCase(item.getCategory()) && item.getAvailable())
                .collect(Collectors.toList());

        if (curries.isEmpty()) return;

        // Add curry explanation
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, DARK_TEXT);
        Paragraph curryTitle = new Paragraph("CURRY / PIRATTAL", sectionFont);
        curryTitle.setAlignment(Element.ALIGN_CENTER);
        curryTitle.setSpacingBefore(40);
        curryTitle.setSpacingAfter(15);
        document.add(curryTitle);

        addSeparatorLine(document);

        // Curry explanation
        Font descFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, GRAY_TEXT);
        Paragraph explanation = new Paragraph(
                "Curry is a broad term for a dish with ample gravy or sauce. Pirattal is a specific, " +
                        "semi dry preparation where ingredients are tossed and coated in a thick spice masala.", descFont);
        explanation.setAlignment(Element.ALIGN_CENTER);
        explanation.setSpacingAfter(25);
        explanation.setIndentationLeft(60);
        explanation.setIndentationRight(60);
        document.add(explanation);

        // Create pittu types header table
        addPittuTypesHeader(document);

        // Add curry items with pittu options
        for (MenuItem item : curries) {
            addCurryWithPittuOptions(document, item);
        }
    }

    private void addPittuTypesHeader(Document document) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(6);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{3, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f});
        headerTable.setSpacingAfter(15);

        // Empty cell for item name column
        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(emptyCell);

        // Pittu type headers
        String[] pittuTypes = {"RED\nPITTU", "WHITE\nPITTU", "KURAKKAN\nPITTU", "KEERAI\nPITTU", "CARROT\nPITTU"};
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD, DARK_TEXT);

        for (String type : pittuTypes) {
            PdfPCell cell = new PdfPCell(new Phrase(type, headerFont));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPaddingBottom(8);
            headerTable.addCell(cell);
        }

        document.add(headerTable);
    }

    private void addCurryWithPittuOptions(Document document, MenuItem item) throws DocumentException {
        PdfPTable itemTable = new PdfPTable(6);
        itemTable.setWidthPercentage(100);
        itemTable.setWidths(new float[]{3, 1.5f, 1.5f, 1.5f, 1.5f, 1.5f});
        itemTable.setSpacingAfter(12);

        // Item name
        Font itemFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, DARK_TEXT);
        PdfPCell nameCell = new PdfPCell(new Phrase(item.getName().toUpperCase(), itemFont));
        nameCell.setBorder(Rectangle.NO_BORDER);
        nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        nameCell.setPaddingRight(10);
        itemTable.addCell(nameCell);

        // Price for each pittu type (using Sri Lankan Rupee format)
        Font priceFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, DARK_TEXT);
        String priceText = item.getPrice().intValue() + "/=";

        for (int i = 0; i < 5; i++) {
            PdfPCell priceCell = new PdfPCell(new Phrase(priceText, priceFont));
            priceCell.setBorder(Rectangle.NO_BORDER);
            priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            itemTable.addCell(priceCell);
        }

        document.add(itemTable);
    }

    private void addDetailedPittuItem(Document document, MenuItem item) throws DocumentException {
        // Pittu name
        Font nameFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, DARK_TEXT);
        Paragraph name = new Paragraph(item.getName().toUpperCase(), nameFont);
        name.setSpacingBefore(20);
        name.setSpacingAfter(8);
        document.add(name);

        // Description
        Font descFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, DARK_TEXT);
        Paragraph description = new Paragraph(item.getDescription(), descFont);
        description.setSpacingAfter(15);
        description.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(description);
    }

    private void addSimpleMenuItem(Document document, MenuItem item) throws DocumentException {
        // Create table for item layout
        PdfPTable itemTable = new PdfPTable(2);
        itemTable.setWidthPercentage(100);
        itemTable.setWidths(new float[]{4, 1});
        itemTable.setSpacingAfter(8);

        // Item name
        Font nameFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, DARK_TEXT);
        PdfPCell nameCell = new PdfPCell(new Phrase(item.getName().toUpperCase(), nameFont));
        nameCell.setBorder(Rectangle.NO_BORDER);
        nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        itemTable.addCell(nameCell);

        // Price (Sri Lankan Rupee format)
        Font priceFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, DARK_TEXT);
        String priceText = item.getPrice().intValue() + "/=";
        PdfPCell priceCell = new PdfPCell(new Phrase(priceText, priceFont));
        priceCell.setBorder(Rectangle.NO_BORDER);
        priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        itemTable.addCell(priceCell);

        document.add(itemTable);
    }

    private void addSeparatorLine(Document document) throws DocumentException {
        PdfPTable line = new PdfPTable(1);
        line.setWidthPercentage(70);
        line.setHorizontalAlignment(Element.ALIGN_CENTER);
        line.setSpacingAfter(15);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderWidthBottom(2);
        cell.setBorderColor(DARK_TEXT);
        cell.setFixedHeight(5);
        line.addCell(cell);

        document.add(line);
    }

    private void addFooter(Document document) throws DocumentException {
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, GRAY_TEXT);
        Paragraph footer = new Paragraph(
                "Whether you're new to Jaffna cuisine or a long time lover of its flavors, " +
                        "our menu promises a warm, satisfying journey into the heart of Sri Lankan tradition.",
                footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setIndentationLeft(60);
        footer.setIndentationRight(60);
        document.add(footer);
    }

    // Alternative method with debug logging
    public byte[] generateMenuPDFWithDebug(List<MenuItem> menuItems) throws DocumentException {
        System.out.println("Starting PDF generation with " + menuItems.size() + " items");

        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            System.out.println("Document opened successfully");

            // Simple test content first
            Font testFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
            Paragraph testParagraph = new Paragraph("GRAND PITTU - TEST", testFont);
            testParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(testParagraph);

            System.out.println("Test content added");

            // Add a simple menu item for testing
            if (!menuItems.isEmpty()) {
                MenuItem firstItem = menuItems.get(0);
                Paragraph itemName = new Paragraph(
                        firstItem.getName() + " - " + firstItem.getPrice() + "/=",
                        new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK));
                document.add(itemName);
                System.out.println("Added item: " + firstItem.getName());
            }

        } finally {
            document.close();
            System.out.println("Document closed. PDF size: " + out.size() + " bytes");
        }

        return out.toByteArray();
    }
}
