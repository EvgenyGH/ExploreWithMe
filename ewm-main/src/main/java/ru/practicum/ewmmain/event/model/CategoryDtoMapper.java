package ru.practicum.ewmmain.event.model;

public class CategoryDtoMapper {
    public static CategoryDto toDto(Category category){
        return new CategoryDto(category.getId(), category.getName());
    }
}
