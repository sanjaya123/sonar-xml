/*
 * SonarQube XML Plugin
 * Copyright (C) 2010-2019 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.plugins.xml.checks;

import java.util.Collections;
import java.util.regex.Pattern;
import org.sonar.check.Rule;
import org.sonar.check.RuleProperty;
import org.sonar.plugins.xml.Utils;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.XmlTextRange;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;

/**
 * RSPEC-103
 */
@Rule(key = SynapsCheck.RULE_KEY)
public class SynapsCheck extends SonarXmlCheck {

  public static final String RULE_KEY = "SynapsCheck";
  private static final int DEFAULT_LENGTH = 120;

  @RuleProperty(
    key = "synapsCheck",
    description = "The maximum authorized line length",
    defaultValue = "" + DEFAULT_LENGTH,
    type = "INTEGER")
  private int maximumLineLength = DEFAULT_LENGTH;

  private static final Pattern RTRIM = Pattern.compile("\\s+$");

  public void setMaximumLineLength(int maximumLineLength) {
    this.maximumLineLength = maximumLineLength;
  }

@Override
  public void scanFile(XmlFile file) {
    int lineNumber = 1;
    for (String line : Utils.splitLines(file.getContents())) {
      String trimLine = trimEndOfLine(line);
      int length = trimLine.length();
      if (trimLine.contains("<script ")) {
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
          String.format("Script mediator exists", length, maximumLineLength),
          Collections.emptyList());
      }else if (trimLine.contains("<bean ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Bean mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }
      else if (trimLine.contains("<pojoCommand ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("POJO mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<router ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Router mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<in ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("In mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<out ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Out mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<conditionalRouter ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("ConditionalRouter mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<enqueue ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Enqueue mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<event ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Event mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<log level=\"full\"")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Log full mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<foreach ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Foreach mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }
      else if (trimLine.contains("<loopback ")){
        XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
        reportIssue(textRange,
                String.format("Loopback mediator exists", length, maximumLineLength),
                Collections.emptyList());
      }else if (trimLine.contains("<endpoint")){
        if (!trimLine.contains("key=")) {
          XmlTextRange textRange = new XmlTextRange(lineNumber, 0, lineNumber, trimLine.length());
          reportIssue(textRange,
                  String.format("Endpoint is not defined with key", length, maximumLineLength),
                  Collections.emptyList());
        }
      }
      lineNumber++;
    }
  }

  private static String trimEndOfLine(String line) {
    return RTRIM.matcher(line).replaceAll("");
  }
}
