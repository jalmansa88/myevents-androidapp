package myevents.almansa.unir.es.myevents.view.interfaces

interface LoadingContentView {
    fun showToast(msg: String)
    fun showContent()
    fun showError()
    fun showLoading()
    fun showEmpty()
}