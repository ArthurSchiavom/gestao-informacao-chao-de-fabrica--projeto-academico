<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;&#xa;</xsl:text>
        <html>
            <head>

                <meta name="viewport" content="width=device-width, initial-scale=1"/>
                <link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/>
                <!-- de https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css -->
                <script src="https://code.jquery.com/jquery-1.11.3.min.js"/>
                <!-- de https://code.jquery.com/jquery-1.11.3.min.js -->
                <script src="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"/>
                <!-- de https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js -->
            </head>
            <body>
                <div data-role="main" class="ui-content">
                    <h1>Chão de Fábrica</h1>
                    <hr></hr>
                    <br></br>
                    
                    <xsl:for-each select="child::*/*">
                        <!-- Quando o elemento tem valores lá dentro, se não tiver, não escreve nada-->
                        <!-- O Select converte o nome indicado no XML num nome com capitalização na primeira letra-->
                        <xsl:if test="count(*) > 0">
                            <div data-role="collapsible">
                              <h1><xsl:value-of
                                      select="
                                          concat(upper-case(substring(local-name(), 1, 1)),
                                          substring(local-name(), 2),
                                          ' '[not(last())]
                                          )"
                                  />
                              </h1>
                                <xsl:for-each select="child::*">
                                    
                                    <div data-role="collapsible">
                                        <h1>
                                            <xsl:value-of
                                                select="concat(upper-case(substring(local-name(), 1, 1)), substring(local-name(), 2), ' '[not(last())])"/>
                                            <xsl:if test="count(attribute::*) = 1"> - <xsl:value-of select="attribute::*"/></xsl:if>
                                        </h1>
                                        <xsl:apply-templates select="*" mode="Elemento"/>
                                    </div>
                                    
                                    <!-- Chama o template que trata dos value objects de cada elemento de dominio-->
                                </xsl:for-each>
                            </div>
                            <!-- Vai buscar um elemento da lista xml individual -->
                        </xsl:if>
                    </xsl:for-each>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="*" mode="Elemento">
        <div data-role="collapsible">
            <h1>
                <xsl:value-of
                    select="concat(upper-case(substring(local-name(), 1, 1)), substring(local-name(), 2), ' '[not(last())])"
                />
            </h1>
            <!-- Verifica se o elemento tem mais subelementos, se tiver chama outra fez este template de forma recursiva até chegar ao ultimo-->
            <xsl:choose>
                <xsl:when test="count(*) > 0">
                    <xsl:apply-templates select="*" mode="Elemento"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="."/>
                </xsl:otherwise>
            </xsl:choose>
        </div>
    </xsl:template>

</xsl:stylesheet>
