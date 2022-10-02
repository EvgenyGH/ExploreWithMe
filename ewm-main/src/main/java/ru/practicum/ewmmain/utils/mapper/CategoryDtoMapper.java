package ru.practicum.ewmmain.utils.mapper;

import ru.practicum.ewmmain.model.event.category.Category;
import ru.practicum.ewmmain.model.event.category.dto.CategoryDto;
import ru.practicum.ewmmain.model.event.category.dto.CategoryNewDto;

/**
 * Маппер класса {@link Category}
 * @author Evgeny S
 */
public class CategoryDtoMapper {
    /**
     * Маппер в класс {@link CategoryDto}
     * @param category экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link CategoryDto} экземпляр.
     */
    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    /**
     * Маппер в класс {@link Category}
     * @param categoryNewDto экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link Category} экземпляр.
     */
    public static Category toCategory(CategoryNewDto categoryNewDto) {
        return new Category(null, categoryNewDto.getName());
    }

    /**
     * Маппер в класс {@link Category}
     * @param categoryDto экземпляр класса для конвертации.
     * @return возвращает конвертированный в класс {@link Category} экземпляр.
     */
    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName());
    }
}