/**
 * Copyright 2014 DNS Belgium vzw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.dnsbelgium.rdap.jackson;

import be.dnsbelgium.vcard.datatype.AbstractList;
import be.dnsbelgium.vcard.datatype.Text;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TextListSerializer extends JsonSerializer<AbstractList.TextList> {

  @Override
  public Class<AbstractList.TextList> handledType() {
    return AbstractList.TextList.class;
  }

  @Override
  public void serialize(AbstractList.TextList value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
    if (value.getValues() == null || value.getValues().size() == 0) {
      jgen.writeNull();
      return;
    }
    for (Text text : value.getValues()) {
      jgen.writeString(text.getStringValue());
    }
  }
}
