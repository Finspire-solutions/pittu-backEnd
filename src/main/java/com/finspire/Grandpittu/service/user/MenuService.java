package com.finspire.Grandpittu.service.user;

import com.finspire.Grandpittu.api.dto.MenuItemDTO;
import com.finspire.Grandpittu.constants.ApplicationConstants;
import com.finspire.Grandpittu.converter.MenuItemsConverter;
import com.finspire.Grandpittu.entity.MenuItem;
import com.finspire.Grandpittu.exception.ServiceException;
import com.finspire.Grandpittu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {
        private final MenuRepository menuRepository;
        private final MenuItemsConverter menuItemsConverter;

    public List<MenuItem> getAllAvailableMenuItems() {
            return menuRepository.findAllByAvailable(true);
    }

    public MenuItem addMenuItem(MenuItemDTO menuItemDTO) {
            return menuRepository.save(menuItemsConverter.convertToEntity(menuItemDTO));
    }

    public MenuItem updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem existingMenuItem = menuRepository.findById(id).orElse(null);
        if (existingMenuItem != null){
            return menuRepository.save(MenuItem.builder()
                            .name(menuItemDTO.getName())
                            .price(menuItemDTO.getPrice())
                            .available(menuItemDTO.getAvailable())
                            .category(menuItemDTO.getCategory())
                            .description(menuItemDTO.getDescription())
                            .imageUrl(menuItemDTO.getImageUrl())
                    .build());
        }else{
            throw new ServiceException(ApplicationConstants.NO_MENU_ITEMS,ApplicationConstants.BAD_REQUEST, HttpStatus.BAD_REQUEST);
        }
    }

    public boolean deleteMenuItem(Long id) {
        MenuItem response = menuRepository.findById(id).orElse(null);
        if (response != null){
            menuRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        Optional<MenuItem> response = menuRepository.findById(id);
        if (response.isPresent()) {
            return response;
        }else {
            throw new ServiceException(ApplicationConstants.NO_MENU_ITEMS,ApplicationConstants.BAD_REQUEST,HttpStatus.BAD_REQUEST);
        }
    }
}
