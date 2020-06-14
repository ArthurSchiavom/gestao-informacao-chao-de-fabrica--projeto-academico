<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;&#xa;</xsl:text>
        <html>
            <body>
                <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1"/>
                    <link rel="stylesheet" href="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css"/>
                    <!-- de https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css -->
                    <script src="https://code.jquery.com/jquery-1.11.3.min.js"/>
                    <!-- de https://code.jquery.com/jquery-1.11.3.min.js -->
                    <script src="https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"/>
                    <!-- de https://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js -->
                </head>
                <div data-role="main" class="ui-content">
                    <xsl:apply-templates select="*"/>
                </div>
            </body>
        </html>
    </xsl:template>

    <!--    Template que vai buscar tudo -->
    <xsl:template match="*">
        <xsl:choose>
            <!--           Envia por parametro uma nova indentação no parametro "indent"-->
            <xsl:when test="count(*)>0"><ol><li><xsl:value-of select="concat(upper-case(substring(local-name(),1,1)),substring(local-name(), 2),' '[not(last())])"/></li><xsl:apply-templates select="*"/></ol></xsl:when>
            <xsl:otherwise><li><xsl:value-of select="."/></li></xsl:otherwise>
        </xsl:choose>
    </xsl:template>

</xsl:stylesheet>
