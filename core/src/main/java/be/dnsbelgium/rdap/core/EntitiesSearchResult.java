package be.dnsbelgium.rdap.core;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({"rdapConformance"})
public class EntitiesSearchResult {

  public Set<String> rdapConformance;
  public List<Entity> entitySearchResults;

  @JsonCreator
  public EntitiesSearchResult(@JsonProperty("entitySearchResults") List<Entity> entitySearchResults) {
    this.entitySearchResults = entitySearchResults;
  }

  public void addRdapConformance(String conformance) {
    if (rdapConformance == null) {
      rdapConformance = new HashSet<String>();
    }
    rdapConformance.add(conformance);
  }
}
