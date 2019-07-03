package org.vaadin.grid.cellrenderers.client.view;

/**
 * Enum used for defining the mode of the RowIndexRenderer
 */ 
public enum RowIndexMode {
	/**
	 * Default, the regular integer index 
	 */ 
	NORMAL,
	/**
	 * Set indeces to be ordinals, i.e. 1st, 2nd, 3rd, ...
	 */ 
	ORDINAL,
	/**
	 * Set indeces to be roman literals.
	 */ 
	ROMAN;
}
