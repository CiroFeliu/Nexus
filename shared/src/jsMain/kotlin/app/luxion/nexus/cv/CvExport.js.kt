package app.luxion.nexus.cv

import web.dom.document
import web.events.EventHandler
import web.html.HtmlSource
import web.html.HtmlTagName

// Web export has no PDF-writing dependency: it loads `content` as a standalone HTML document
// into a hidden iframe and prints there via that iframe's own window — see CvPrintHtml.kt for
// why printing happens in a separate browsing context rather than the main document. "Save as
// PDF" in the resulting native print dialog produces the file.
actual fun exportCvToPdf(content: CvContent) {
    val iframe = document.createElement(HtmlTagName.iframe)
    iframe.style.position = "fixed"
    iframe.style.width = "0"
    iframe.style.height = "0"
    iframe.style.border = "none"
    // srcdoc loads asynchronously, so print() has to wait for onload rather than running
    // right after the assignment below, or it'd print the iframe's still-blank prior content.
    iframe.onload = EventHandler {
        val printWindow = iframe.contentWindow
        printWindow?.onafterprint = EventHandler { iframe.remove() }
        printWindow?.print()
    }
    // srcdoc must be set before the iframe is inserted into the document: an empty iframe
    // navigates to about:blank on insertion, firing onload before the real content is ready
    // and printing a blank page. Setting srcdoc first makes that the iframe's only navigation.
    iframe.srcdoc = HtmlSource(content.toPrintDocument())
    document.body.appendChild(iframe)
}
