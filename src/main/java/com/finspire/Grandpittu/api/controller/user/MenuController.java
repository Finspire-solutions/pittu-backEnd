package com.finspire.Grandpittu.api.controller.user;

import com.finspire.Grandpittu.api.dto.MenuItemDTO;
import com.finspire.Grandpittu.constants.ApplicationConstants;
import com.finspire.Grandpittu.entity.MenuItem;
import com.finspire.Grandpittu.exception.ServiceException;
import com.finspire.Grandpittu.service.user.MenuService;
import com.finspire.Grandpittu.service.user.PDFService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
@Validated
public class MenuController {

    private final MenuService menuService;
    private final PDFService pdfService;
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems(){
        try {
            List<MenuItem> menuItems = menuService.getAllAvailableMenuItems();
            return ResponseEntity.ok(menuItems);
        }catch (Exception ex){
            throw new ServiceException(ApplicationConstants.NO_MENU_ITEMS,ApplicationConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody @Valid MenuItemDTO menuItemDTO) {
        try {
            MenuItem savedItem = menuService.addMenuItem(menuItemDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedItem);

        } catch (Exception e) {
            throw new ServiceException(ApplicationConstants.BAD_REQUEST,ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuItemDTO menuItemDTO) {
        try {
            return ResponseEntity.ok(menuService.updateMenuItem(id, menuItemDTO));
        } catch (Exception e) {
            throw new ServiceException(ApplicationConstants.BAD_REQUEST,ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        try {
            boolean deleted = menuService.deleteMenuItem(id);
            if (deleted) {
                return ResponseEntity.ok("Deleted Successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new ServiceException(ApplicationConstants.FAILED_TO_DELETE_MENU_ITEM,ApplicationConstants.FAILED_TO_DELETE_MENU_ITEM,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable Long id) {
        try {
            return menuService.getMenuItemById(id)
                    .map(item -> ResponseEntity.ok(item))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            throw new ServiceException(ApplicationConstants.NO_MENU_ITEMS,ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadMenuPDF() {
        try {
            List<MenuItem> menuItems = menuService.getAllAvailableMenuItems(); // Get entities for PDF
            byte[] pdfBytes = pdfService.generateGrandPittuMenuPDF(menuItems);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "grand_pittu_menu.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/view")
    public ResponseEntity<byte[]> viewMenuPDF() {
        try {
            List<MenuItem> menuItems = menuService.getAllAvailableMenuItems();
            byte[] pdfBytes = pdfService.generateGrandPittuMenuPDF(menuItems);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "inline; filename=grand_pittu_menu.pdf"); // 👈 inline instead of attachment
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
