package myevents.almansa.unir.es.myevents.view.interfaces

interface LoadingContentView: ShowToast, ShowError {
    fun showContent()
    fun showLoading()
    fun showEmpty()
}