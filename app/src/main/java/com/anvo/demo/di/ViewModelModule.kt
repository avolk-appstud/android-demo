//package com.dupuis.webtoonfactory.di
//
//import com.dupuis.webtoonfactory.ui.catalog.CatalogViewModel
//import com.dupuis.webtoonfactory.ui.coin.CoinViewModel
//import com.dupuis.webtoonfactory.ui.comment.CommentViewModel
//import com.dupuis.webtoonfactory.ui.downloads.DownloadViewModel
//import com.dupuis.webtoonfactory.ui.home.HomeViewModel
//import com.dupuis.webtoonfactory.ui.login.AuthViewModel
//import com.dupuis.webtoonfactory.ui.onboarding.OnboardingViewModel
//import com.dupuis.webtoonfactory.ui.reader.ReaderViewModel
//import com.dupuis.webtoonfactory.ui.serie.SerieViewModel
//import com.dupuis.webtoonfactory.ui.settings.AccountViewModel
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.dsl.module
//
//val viewModelModule = module {
//    viewModel { AuthViewModel(get()) }
//    viewModel { HomeViewModel(get()) }
//    viewModel { AccountViewModel(get(), get(), get(), get()) }
//    viewModel { CatalogViewModel(get()) }
//    viewModel { SerieViewModel(get(), get(), get(), get()) }
//    viewModel { ReaderViewModel(get(), get(), get()) }
//    viewModel { CommentViewModel(get()) }
//    viewModel { DownloadViewModel(get()) }
//    viewModel { OnboardingViewModel(get(), get()) }
//    viewModel { CoinViewModel(get(), get(), get()) }
//}
