package org.vaadin.grid.cellrenderers.editable.common;

import java.lang.reflect.Field;

import com.vaadin.data.Container.Indexed;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;

public class EditableRendererUtil {

	public static boolean isColumnComponentEnabled(Object itemId, com.vaadin.ui.Grid parentGrid,
			EditableRendererEnabled editableRendererEnabled) {
		if (editableRendererEnabled == null) {
			return true;
		}

		try {
			Indexed containerDataSource = parentGrid.getContainerDataSource();

			if (containerDataSource instanceof GeneratedPropertyContainer) {
				GeneratedPropertyContainer generatedPropertyContainer = (GeneratedPropertyContainer) containerDataSource;
				Field wrappedContainerField = generatedPropertyContainer.getClass()
					.getDeclaredField("wrappedContainer");
				wrappedContainerField.setAccessible(true);

				containerDataSource = (Indexed) wrappedContainerField.get(generatedPropertyContainer);
			}

			if (containerDataSource instanceof BeanItemContainer) {
				BeanItemContainer beanItemContainer = (BeanItemContainer) containerDataSource;
				return editableRendererEnabled.isEnabled(beanItemContainer.getItem(itemId)
					.getBean());
			}

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return true;
	}

}
