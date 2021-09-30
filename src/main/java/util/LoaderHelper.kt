package util

import java.io.File

class LoaderHelper {}

fun String.asResourceFile() =
    LoaderHelper::class.java.getResource(this).let { url -> File(url.toURI())}