package ru.practicum.ewmmain.event.model.category;

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
