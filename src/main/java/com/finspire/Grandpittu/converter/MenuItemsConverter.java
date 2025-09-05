package com.finspire.Grandpittu.converter;

import com.finspire.Grandpittu.api.dto.MenuItemDTO;
import com.finspire.Grandpittu.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuItemsConverter {
        public List<MenuItemDTO> convert(List<MenuItem> menuItem){
           return menuItem.stream()
                   .map(this::convertSingle).toList();
        }

    private MenuItemDTO convertSingle(MenuItem menuItem) {
        return MenuItemDTO.builder()
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .price(menuItem.getPrice())
                .category(menuItem.getCategory())
                .available(menuItem.getAvailable())
                .imageUrl(menuItem.getImageUrl())
                .build();
    }

//    reverse conversion for save
    public MenuItem convertToEntity(MenuItemDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setCategory(dto.getCategory());
        menuItem.setAvailable(dto.getAvailable());
        menuItem.setImageUrl(dto.getImageUrl());
        return menuItem;
    }

}
