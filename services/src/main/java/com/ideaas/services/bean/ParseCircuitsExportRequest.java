package com.ideaas.services.bean;

import com.ideaas.services.domain.MapUbicaionParametro;
import com.ideaas.services.request.ExportCircuitRequest;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.DocumentRenderer;
import com.itextpdf.layout.renderer.TableRenderer;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.*;
import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

/**
 * The Parse circuits export request. The function of this class is to analyze the request to
 * export circuits, and generate a report in Excel or PDF, to return it as an array of bytes.
 *
 * @todo replace the method to assign a customizable colors when generating the styles for an excel cell
 * @todo make all public methods static
 */
public class ParseCircuitsExportRequest {
    //#region properties and constructor
    /**
     * The export circuit request
     */
    private ExportCircuitRequest circuitsExportRequest;

    /**
     * The file header. This header must be repeated in all the pages of the file
     */
    private List<List<MyCell>> header;

    /**
     * The table. The table of locations. The table header must be repeated on all pages
     */

    private List<List<MyCell>> table;

    /**
     * The company logo height in pixels.
     */
    private final int COMPANY_LOGO_HEIGHT = 45;

    private final MyCellStyle DEFAULT_STYLE =
            new MyCellStyle(
                "Calibri",
                11,
                1,
                false,
                new int[]{0, 0, 0},
                new int[]{255, 255, 255},
                false
            );;
    /**
     * Bold cell style
     */

    private final MyCellStyle BOLD_STYLE = MyCellStyle.from(DEFAULT_STYLE).setBold(true);

    /**
     * Styles for cells that occupy 2 columns.
     */
    private final MyCellStyle DOUBLE_CELL = MyCellStyle.from(DEFAULT_STYLE).setSpan(2);

    /**
     * Table header cell style.
     */
    private final MyCellStyle HEADER_TABLE_STYLE = MyCellStyle.from(DEFAULT_STYLE).setBorder(true).setBold(true).setRbgFontColor(new int[]{255, 255, 255}).setRbgBackgroundColor(new int[]{124, 124, 124});

    /**
     * Table body cell style.
     */
    private final MyCellStyle TABLE_CELL = MyCellStyle.from(DEFAULT_STYLE).setBorder(true);

    /**
     * The locations that are used to populate the table.
     */
    List<MapReservationDTO> locations;

    /**
     * The parameters locations that are used to populate the table.
     */
    List<MapUbicaionParametro> parameters;

    /**
     * The file server url.
     */
    private String urlFileServer;

    /**
     * Instantiates a new Parse circuits export request.
     *
     * @param circuitsExportRequest the circuits export request
     * @param locations             the locations
     * @param parameters            the parameters
     */
    public ParseCircuitsExportRequest(ExportCircuitRequest circuitsExportRequest, List<MapReservationDTO> locations, List<MapUbicaionParametro> parameters, String urlFileServer) throws MalformedURLException {
        this.circuitsExportRequest = circuitsExportRequest;
        this.locations   = locations;
        this.parameters = parameters;
        this.urlFileServer = urlFileServer;
        this.header = new ArrayList<>();
        this.table  = new ArrayList<>();
        String url = this.urlFileServer+"/fotos_map/map-company-logos/"+circuitsExportRequest.getCompanyId()+"/logo.jpg";
        InputStream is = null;
        try {
            is = new URL(url).openStream();
            circuitsExportRequest.setImage(IOUtils.toByteArray(is));
        } catch (IOException e) {
            e.printStackTrace ();
        } finally {
            if (circuitsExportRequest.getImage() == null) {
                circuitsExportRequest.setImage(new byte[0]);
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //#endregion properties and constructor

    //#region parse request
    /**
     * This function parses the circuit export request and transforms it into two arrays, one that represents
     * the document header, and the other that represents the table.
     */
    private void parseRequest() {
        // Parse the document header
        if(circuitsExportRequest.getImage().length > 0) this.header.add(Arrays.asList(new MyCell(""), new MyCell("")));

        this.header.add(Arrays.asList(new MyCell("CLIENTE:", BOLD_STYLE),new MyCell(this.circuitsExportRequest.getClient(), DEFAULT_STYLE)));
        this.header.add(Arrays.asList(new MyCell("CAMPAÑA:", BOLD_STYLE),new MyCell(this.circuitsExportRequest.getCampaign(), DEFAULT_STYLE)));
        this.header.add(Arrays.asList(new MyCell("FECHA DESDE:", BOLD_STYLE),new MyCell(this.circuitsExportRequest.getStartDate(), DEFAULT_STYLE)));
        this.header.add(Arrays.asList(new MyCell("FECHA HASTA:", BOLD_STYLE),new MyCell(this.circuitsExportRequest.getEndDate(), DEFAULT_STYLE)));

        if(circuitsExportRequest.includeObservations())     this.header.add(Arrays.asList(new MyCell(this.circuitsExportRequest.getObservations(), DOUBLE_CELL.setSpan(2))));
        if(circuitsExportRequest.isSubjectToAvailability()) this.header.add(Arrays.asList(new MyCell("SUJETO A DISPONIBILIDAD", DOUBLE_CELL.setSpan(2).setBorder(false))));

        this.header.add(Arrays.asList(new MyCell(this.circuitsExportRequest.getListType(), DOUBLE_CELL.setSpan(2).setBorder(false))));
        this.header.add(Arrays.asList(new MyCell("CANTIDAD TOTAL: "+Long.toString(this.locations.stream().map(MapReservationDTO::getAmount).map(Optional::ofNullable).map( opt -> opt.orElse(1L) ).reduce((a, b) -> a+b).orElse(0L)),BOLD_STYLE)));

        // Add an empty row to separate the document header from the table
        this.header.add(Arrays.asList(new MyCell(""), new MyCell("")));

        // Parse the document table
        List headerLocationsTable = new ArrayList<>();
        this.table.add(headerLocationsTable);

        List<Consumer<MapReservationDTO>> locationsMappers = new ArrayList<>();
        if(this.circuitsExportRequest.includeReferenceId()) {
            headerLocationsTable.add(new MyCell("ID Referencia", HEADER_TABLE_STYLE));
            locationsMappers.add((MapReservationDTO location) -> {
                if (location.getReferenceId() != null) {
                    table.get(table.size()-1).add(new MyCell(location.getReferenceId(), TABLE_CELL));
                } else {
                    table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
                }
            });
        }
        if(this.circuitsExportRequest.includeAddress()) {
            headerLocationsTable.add(new MyCell("Dirección", HEADER_TABLE_STYLE));
            locationsMappers.add((MapReservationDTO location) -> {
                if (location.getDirection() != null) {
                    table.get(table.size()-1).add(new MyCell(location.getDirection(), TABLE_CELL));
                } else {
                    table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
                }
            });
        }

        if(this.circuitsExportRequest.includeCity()) {
            headerLocationsTable.add(new MyCell("Localidad", HEADER_TABLE_STYLE));
            locationsMappers.add((MapReservationDTO location) -> {
                if (location.getCity() != null) {
                    table.get(table.size()-1).add(new MyCell(location.getCity(), TABLE_CELL));
                } else {
                    table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
                }
            });
        }

        if(this.circuitsExportRequest.includeElement()) {
            headerLocationsTable.add(new MyCell("Elemento", HEADER_TABLE_STYLE));
            locationsMappers.add((MapReservationDTO location) -> {
                if (location.getElement() != null) {
                    table.get(table.size() - 1).add(new MyCell(location.getElement().toString(), TABLE_CELL));
                } else {
                    table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
                }
            });
        }

        if(this.circuitsExportRequest.includeCoordinates()) {
            headerLocationsTable.add(new MyCell("Latitud", HEADER_TABLE_STYLE));
            locationsMappers.add((MapReservationDTO location) -> {
                if (location.getLatitude() != null) {
                    table.get(table.size() - 1).add(new MyCell(location.getLatitude().toString(), TABLE_CELL));
                } else {
                    table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
                }
            });

            headerLocationsTable.add(new MyCell("Longitud", HEADER_TABLE_STYLE));
            locationsMappers.add((MapReservationDTO location) -> {
                if (location.getLongitude() != null) {
                    table.get(table.size() - 1).add(new MyCell(location.getLongitude().toString(), TABLE_CELL));
                } else {
                    table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
                }
            });
        }

        if(!this.circuitsExportRequest.getParameters().isEmpty()) {
            circuitsExportRequest.getParameters().entrySet().stream().forEach(parametter -> {
                if (parametter.getValue() != null){
                    headerLocationsTable.add(new MyCell(parametter.getValue(), HEADER_TABLE_STYLE));
                } else {
                    headerLocationsTable.add(new MyCell(null, HEADER_TABLE_STYLE));
                }
            });

            locationsMappers.add((MapReservationDTO location) -> {
                circuitsExportRequest.getParameters().entrySet().stream().forEach(parametter -> {
                    boolean added = parameters.stream().anyMatch(p -> {
                        boolean exists = p.getId().getIdParametro().equals(Long.valueOf(parametter.getKey())) && p.getMapUbicacion().getId().equals(location.getId());
                        
                        if (exists) table.get(table.size()-1).add(new MyCell(p.getDescripcion(), TABLE_CELL));
                        
                        return exists;
                    });
                    if(!added) table.get(table.size()-1).add(new MyCell("", TABLE_CELL));
                });
            });
        }

        headerLocationsTable.add(new MyCell("Cantidad", HEADER_TABLE_STYLE));
        locationsMappers.add((MapReservationDTO location) -> {
            if (location.getAmount() != null) {
                    table.get(table.size()-1).add(new MyCell(location.getAmount().toString(), TABLE_CELL));
            } else {
                table.get(table.size() - 1).add(new MyCell(null, TABLE_CELL));
            }
        });

        this.locations.forEach(location -> {
            // Add new row
            table.add(new ArrayList<>(headerLocationsTable.size()));
            // Fill row with location data
            locationsMappers.forEach(callback -> callback.accept(location));
        });
    }
    //#endregion

    //#region build Excel byte array
    /**
     * Assemble all the parts of the Excel and transform it into a byte array.
     *
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] buildExcel() throws IOException {
        this.parseRequest();
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet     = wb.createSheet("Circuitos");

         for (int i = 0; i < header.size(); i++) {
             List<MyCell> row = header.get(i);
             sheet.createRow(i);
             for (int j = 0; j < row.size(); j++) {
                 MyCell col = row.get(j);
                 Cell cell = sheet.getRow(i).createCell(j);
                 if(col.style.span > 1) {
                     sheet.addMergedRegion(new CellRangeAddress(i, i, 0, col.style.span - 1));
                 }
                 cell.setCellStyle(getExcelCell(col, wb));
                 cell.setCellValue(col.content);
             }
         }

         for (int i = 0; i < table.size(); i++) {
             List<MyCell> row = table.get(i);
             sheet.createRow(header.size() + i);
             for (int j = 0; j < row.size(); j++) {
                MyCell col = row.get(j);
                Cell cell = sheet.getRow(header.size() + i).createCell(j);
                cell.setCellStyle(getExcelCell(col, wb));
                cell.setCellValue(col.content);
                sheet.autoSizeColumn(j);
             }
         }

        if(circuitsExportRequest.getImage().length > 0) {
            sheet.getRow(0).setHeightInPoints(new Double(Units.pixelToPoints(COMPANY_LOGO_HEIGHT)).longValue());
            CreationHelper helper = wb.getCreationHelper();
            Drawing drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setAnchorType( ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE );
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(circuitsExportRequest.getImage()));
            BufferedImage resizeimg = Scalr.resize(img, COMPANY_LOGO_HEIGHT * (img.getWidth() / img.getHeight()), COMPANY_LOGO_HEIGHT);;
            ByteArrayOutputStream resizedImgByte = new ByteArrayOutputStream();
            ImageIO.write(resizeimg, "png", resizedImgByte);
            int pictureIndex = wb.addPicture( resizedImgByte.toByteArray(), Workbook.PICTURE_TYPE_JPEG);
            anchor.setCol1( 0 ); anchor.setRow1( 0 ); anchor.setRow2( 0 ); anchor.setCol2( 1 );
            Picture pict = drawing.createPicture( anchor, pictureIndex );
            pict.resize();
        }

        PrintSetup printSetup = sheet.getPrintSetup();
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short) 0);
        // add one to include the first row of the table
        sheet.createFreezePane(0,header.size()+1);
        sheet.setRepeatingRows(CellRangeAddress.valueOf("1:"+(header.size()+1)));

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        os.close();
        return os.toByteArray();
    }

    private CellStyle getExcelCell(MyCell cell, XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();
        XSSFFont styleFont  = wb.createFont();
        styleFont.setFontHeightInPoints((short) cell.style.fontSize);
        styleFont.setFontName(cell.style.fontFamily);
        styleFont.setBold(cell.style.bold);
        styleFont.setItalic(false);
        style.setFont(styleFont);
        styleFont.setColor(
            new XSSFColor(new java.awt.Color(
                cell.style.rbgFontColor[0],
                cell.style.rbgFontColor[1],
                cell.style.rbgFontColor[2])));
            // Didn't work
        style.setFillForegroundColor(
                new XSSFColor(new java.awt.Color(
                    cell.style.rbgBackgroundColor[0],
                    cell.style.rbgBackgroundColor[1],
                    cell.style.rbgBackgroundColor[2])));
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // if(cell.style.rbgBackgroundColor[0] != 255){
        //     style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        // }
        if(cell.style.border) {
            style.setBorderTop   (BorderStyle.MEDIUM);
            style.setBorderBottom(BorderStyle.MEDIUM);
            style.setBorderLeft  (BorderStyle.MEDIUM);
            style.setBorderRight (BorderStyle.MEDIUM);
        }
        return style;
    }
    //#endregion

    //#region build PDF byte array
    /**
     * Assemble all the parts of the PDF and transform it into a byte array.
     *
     * @return the byte [ ]
     * @throws IOException                  the io exception
     * @throws InterruptedException         the interrupted exception
     * @throws ParserConfigurationException the parser configuration exception
     * @throws TransformerException         the transformer exception
     */
    public byte[] buildPDF() throws IOException, InterruptedException, ParserConfigurationException, TransformerException {
        this.parseRequest();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfDocument pdf = new PdfDocument(new PdfWriter(os));
        try (Document document = new Document(pdf)) {
            TableHeaderEventHandler handler = new TableHeaderEventHandler(document);
            pdf.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
            Table table = new Table(this.table.get(0).size());
            // Add table header
            this.table.remove(0).forEach( col -> table.addHeaderCell(this.getPDFCell(col)) );

            // Add table content
            this.table.forEach( row -> row.forEach(col -> table.addCell(this.getPDFCell(col))));
            table.useAllAvailableWidth();
            document.add(table);
        }
        return os.toByteArray();
    }

    /**
     * Transform  a {@link MyCell simple cell} to a {@link com.itextpdf.layout.element.Cell PDF Cell}
     *
     * @param cell
     * @return the cell
     */
    private com.itextpdf.layout.element.Cell getPDFCell(MyCell cell) {
        com.itextpdf.layout.element.Cell pdfCell = new com.itextpdf.layout.element.Cell(1, cell.style.span);
        try {
            pdfCell.add(new Paragraph(Optional.ofNullable(cell.content).orElse(""))
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
                .setFontSize(cell.style.fontSize - 2)
                .setFontColor(new DeviceRgb(cell.style.rbgFontColor[0], cell.style.rbgFontColor[1], cell.style.rbgFontColor[2])))
                .setBackgroundColor(new DeviceRgb(cell.style.rbgBackgroundColor[0], cell.style.rbgBackgroundColor[1], cell.style.rbgBackgroundColor[2]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(cell.style.bold) pdfCell.setBold();
        if(!cell.style.border) pdfCell.setBorder(Border.NO_BORDER);
        return pdfCell;
    }

    /**
     * Class to handle the creation event of a page, to add the {@link ParseCircuitsExportRequest#header} to each
     * page. Most of this code is based on the IText documentation. For more information visit the
     * <a href="https://kb.itextpdf.com/home/it7kb/examples/page-events-for-headers-and-footers">official docs</a>
     */
    private class TableHeaderEventHandler implements IEventHandler {
        private Table table;
        private float tableHeight;
        private Document doc;

        /**
         * Instantiates a new Table header event handler.
         *
         * @param doc the doc
         */
        public TableHeaderEventHandler(Document doc) {
            this.doc = doc;
            // Calculate top margin to be sure that the table will fit the margin.
            table = new Table(UnitValue.createPercentArray( new float[] {20f,80f} ))
                    .setWidth(100f)
                    .setFixedLayout()
                    .useAllAvailableWidth();
            if(circuitsExportRequest.getImage().length > 0) {
                ImageData data = ImageDataFactory.create(circuitsExportRequest.getImage());
                Image img = new Image(data);
                img.setHeight(COMPANY_LOGO_HEIGHT);

                com.itextpdf.layout.element.Cell logo = new com.itextpdf.layout.element.Cell(1, 2)
                        .setBorder(Border.NO_BORDER)
                        .add(img);
                table.addCell(logo);
            }

            header.forEach( row -> row.forEach(col -> table.addCell(getPDFCell(col))));
            TableRenderer renderer = (TableRenderer) table.createRendererSubTree();
            renderer.setParent(new DocumentRenderer(doc));

            // Simulate the positioning of the renderer to find out how much space the header table will occupy.
            LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(0, PageSize.A4)));
            tableHeight = result.getOccupiedArea().getBBox().getHeight();

            // set top margin
            float topMargin = 36 + getTableHeight();
            doc.setMargins(topMargin, 36, 36, 36);
        }

        @Override
        public void handleEvent(Event currentEvent) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            float coordX = pageSize.getX() + doc.getLeftMargin();
            float coordY = pageSize.getTop() - doc.getTopMargin();
            float width = pageSize.getWidth() - doc.getRightMargin() - doc.getLeftMargin();
            float height = getTableHeight();
            Rectangle rect = new Rectangle(coordX, coordY, width, height);

            new Canvas(canvas, rect)
                    .add(table)
                    .close();
        }

        /**
         * Gets table height.
         *
         * @return the table height
         */
        public float getTableHeight() {
            return tableHeight;
        }
    }
    //#endregion

    //#region utils private inner classes
    /**
     * A class to abstract the differences between Apche Poi and Excel Cells
     */
    private class MyCell {
        String content;
        MyCellStyle style;

        /**
         * Instantiates a new My cell.
         *
         * @param content the content
         */
        public MyCell(String content) {
            this.content = content;
            this.style = DEFAULT_STYLE;
        }

        /**
         * Instantiates a new My cell.
         *
         * @param content the content
         * @param style   the style
         */
        public MyCell(String content, MyCellStyle style) {
            this.content = content;
            this.style = style;
        }
    }

    /**
     * A class to abstract the differences between Apache Poi and Excel Cells Styles
     */
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MyCellStyle {
        String fontFamily;
        int fontSize;
        /**
         * The number of columns a cell occupies
         */
        int span;
        boolean bold;
        int[] rbgFontColor;
        int[] rbgBackgroundColor;
        boolean border;

        /**
         * From my cell style.
         *
         * @param cellStyle the cell style
         * @return the my cell style
         */
        static MyCellStyle from(MyCellStyle cellStyle) {
            return new MyCellStyle()
                    .setFontFamily(cellStyle.fontFamily)
                    .setFontSize(cellStyle.fontSize)
                    .setSpan(cellStyle.span)
                    .setBold(cellStyle.bold)
                    .setRbgFontColor(cellStyle.rbgFontColor)
                    .setRbgBackgroundColor(cellStyle.rbgBackgroundColor)
                    .setBorder(cellStyle.border);
        }

        /**
         * Sets font family.
         *
         * @param fontFamily the font family
         * @return the font family
         */
        public MyCellStyle setFontFamily(String fontFamily) {
            this.fontFamily = fontFamily;
            return this;
        }

        /**
         * Sets font size.
         *
         * @param fontSize the font size
         * @return the font size
         */
        public MyCellStyle setFontSize(int fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        /**
         * Sets span.
         *
         * @param span the span
         * @return the span
         */
        public MyCellStyle setSpan(int span) {
            this.span = span;
            return this;
        }

        /**
         * Sets bold.
         *
         * @param bold the bold
         * @return the bold
         */
        public MyCellStyle setBold(boolean bold) {
            this.bold = bold;
            return this;
        }

        /**
         * Sets rbg font color.
         *
         * @param rbgFontColor the rbg font color
         * @return the rbg font color
         */
        public MyCellStyle setRbgFontColor(int[] rbgFontColor) {
            this.rbgFontColor = rbgFontColor;
            return this;
        }

        /**
         * Sets rbg background color.
         *
         * @param rbgBackgroundColor the rbg background color
         * @return the rbg background color
         */
        public MyCellStyle setRbgBackgroundColor(int[] rbgBackgroundColor) {
            this.rbgBackgroundColor = rbgBackgroundColor;
            return this;
        }

        /**
         * Sets border.
         *
         * @param border the border
         * @return the border
         */
        public MyCellStyle setBorder(boolean border) {
            this.border = border;
            return this;
        }
    }
    //#endregion
}