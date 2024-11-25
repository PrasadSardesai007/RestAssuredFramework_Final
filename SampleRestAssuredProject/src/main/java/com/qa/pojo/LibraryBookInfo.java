package com.qa.pojo;

import java.util.List;

public class LibraryBookInfo {
	
	public LibraryBookInfo(List<Library> library) {
		
		this.library = library;
	}

	private List<Library> library;

	public List<Library> getLibrary() {
		return library;
	}

	public void setLibrary(List<Library> library) {
		this.library = library;
	}
	
	

}
