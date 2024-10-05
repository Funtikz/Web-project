package org.example.magazinexampleproject.mappers;

import org.example.magazinexampleproject.dto.ClothingDto;
import org.example.magazinexampleproject.models.ClothingProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClothingMapper {
    ClothingProduct toEntity(ClothingDto clothingDto);
    ClothingDto toDto(ClothingProduct clothingProduct);
    List<ClothingDto> listToDto(List<ClothingProduct> clothingProducts);
    List<ClothingProduct> listToEntity(List<ClothingDto> clothingDtos);
}
