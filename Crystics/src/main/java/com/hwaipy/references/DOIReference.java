package com.hwaipy.references;

/**
 *
 * @author Hwaipy
 */
public class DOIReference implements Reference {

  private final DOI doi;
  private String description;

  public DOIReference(DOI doi) {
    this.doi = doi;
  }

  public DOIReference(DOI doi, String description) {
    this.doi = doi;
    this.description = description;
  }

  @Override
  public String getDescription() {
    if (description == null) {
      return "DOI:" + doi.getDOIString();
    }
    else {
      return description;
    }
  }

}
