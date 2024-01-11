package com.barangbareng.viewmodels

import androidx.lifecycle.ViewModel
import com.barangbareng.repository.PreferencesRepository
import com.barangbareng.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val remoteRepository: RemoteRepository,
    val preferencesRepository: PreferencesRepository
) : ViewModel() {

}