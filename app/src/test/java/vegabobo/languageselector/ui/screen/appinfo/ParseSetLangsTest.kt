package vegabobo.languageselector.ui.screen.appinfo

import org.junit.Assert.assertEquals
import org.junit.Test

class ParseSetLangsTest {

    @Test
    fun `parseSetLangs returns single locale without commas`() {
        val set = setOf("English,en")

        val locales = set.parseSetLangs()

        assertEquals(1, locales.size)
        assertEquals("English", locales[0].name)
        assertEquals("en", locales[0].languageTag)
    }

    @Test
    fun `parseSetLangs preserves names containing commas`() {
        val set = setOf("Portuguese, Brazil,pt-BR")

        val locales = set.parseSetLangs()

        assertEquals(1, locales.size)
        assertEquals("Portuguese, Brazil", locales[0].name)
        assertEquals("pt-BR", locales[0].languageTag)
    }
}
