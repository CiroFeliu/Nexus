package app.luxion.nexus.cv

import web.dom.document
import web.events.EventHandler
import web.html.HtmlSource
import web.html.HtmlTagName

actual fun exportCvToPdf(content: CvContent) {
    val iframe = document.createElement(HtmlTagName.iframe)
    iframe.style.position = "fixed"
    iframe.style.width = "0"
    iframe.style.height = "0"
    iframe.style.border = "none"
    iframe.onload = EventHandler {
        val printWindow = iframe.contentWindow
        printWindow?.onafterprint = EventHandler { iframe.remove() }
        printWindow?.print()
    }
    iframe.srcdoc = HtmlSource(content.toPrintDocument())
    document.body.appendChild(iframe)
}
