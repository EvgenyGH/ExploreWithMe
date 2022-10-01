package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto;

public class CategoryDtoMapper {
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static Category toCategory(CategoryNewDto categoryNewDto) {
        return new Category(null, categoryNewDto.getName());
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}
