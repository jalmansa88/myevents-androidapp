package myevents.almansa.unir.es.myevents.view.interfaces

interface LoadingContentView: ShowToast {
    fun showContent()
    fun showError()
    fun showLoading()
    fun showEmpty()
}