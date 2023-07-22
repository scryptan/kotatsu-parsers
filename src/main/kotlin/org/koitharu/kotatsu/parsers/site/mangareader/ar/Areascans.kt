package org.koitharu.kotatsu.parsers.site.mangareader.ar

import org.koitharu.kotatsu.parsers.MangaLoaderContext
import org.koitharu.kotatsu.parsers.MangaSourceParser
import org.koitharu.kotatsu.parsers.config.ConfigKey
import org.koitharu.kotatsu.parsers.model.MangaSource
import org.koitharu.kotatsu.parsers.site.mangareader.MangaReaderParser
import java.text.SimpleDateFormat
import java.util.Locale

@MangaSourceParser("AREASCANS", "Areascans", "ar")
internal class Areascans(context: MangaLoaderContext) :
	MangaReaderParser(context, MangaSource.AREASCANS, pageSize = 20, searchPageSize = 10) {

	override val configKeyDomain: ConfigKey.Domain
		get() = ConfigKey.Domain("www.areascans.net")

	override val chapterDateFormat: SimpleDateFormat = SimpleDateFormat("MMMM d, yyyy", Locale("ar", "AR"))

}
