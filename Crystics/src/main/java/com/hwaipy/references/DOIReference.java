/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
