package com.example.businesslayer;

public enum Category {
	ANIMALS("Animals"),
	BIRDS("Birds"),
	INSECTS("Insects"),
	ROCKS("Rocks"),
	TREES("Trees"),
	OTHER("Other");
	//field
    private final String category_name;
    //constructor private!?
	Category(String category_name) {
        this.category_name = category_name;
    }
	public String getCategory_name() {
		return category_name;
	}

}
