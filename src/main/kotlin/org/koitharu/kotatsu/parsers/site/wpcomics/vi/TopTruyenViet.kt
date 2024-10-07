package org.koitharu.kotatsu.parsers.site.wpcomics.vi

import org.jsoup.nodes.Document
import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.model.MangaChapter
import org.koitharu.kotatsu.parsers.model.MangaPage
import org.koitharu.kotatsu.parsers.model.MangaParserSource
import org.koitharu.kotatsu.parsers.site.wpcomics.WpComicsParser
import org.koitharu.kotatsu.parsers.util.*
import java.text.SimpleDateFormat
import java.util.*
import org.koitharu.kotatsu.parsers.Broken

@Broken
@MangaSourceParser("TOPTRUYENVIET", "TopTruyen.pro", "vi")
internal class TopTruyenViet(context: MangaLoaderContext) :
	WpComicsParser(context, MangaParserSource.TOPTRUYENVIET, "www.toptruyen68.pro", 36) {

    override val selectPage = "div.page-chapter > img, div.chapter-content > img"
    override val selectChapter = "div.list-chapter > a"
    override val selectDate = "div.col-xs-4"
    override val datePattern = "dd/MM/yyyy"

    override suspend fun getChapters(doc: Document): List<MangaChapter> {
        return doc.select(selectChapter).mapChapters(reversed = true) { i, a ->
            val href = a.attrAsRelativeUrl("href")
            val dateText = a.selectFirst(selectDate)?.text()
            val dateFormat = SimpleDateFormat(datePattern, sourceLocale)
            MangaChapter(
                id = generateUid(href),
                name = a.text(),
                number = i + 1f,
                url = href,
                uploadDate = parseChapterDate(
                    dateFormat,
                    dateText,
                ),
                source = source,
                scanlator = null,
                branch = null,
                volume = 0,
            )
        }
    }

    override suspend fun getPages(chapter: MangaChapter): List<MangaPage> {
        val fullUrl = chapter.url.toAbsoluteUrl(domain)
        val doc = webClient.httpGet(fullUrl).parseHtml()
        return doc.select(selectPage).map { img ->
            val url = img.src()?.toRelativeUrl(domain) ?: img.parseFailed("Image src not found")
            MangaPage(
                id = generateUid(url),
                url = url,
                preview = null,
                source = source,
            )
        }
    }
}
