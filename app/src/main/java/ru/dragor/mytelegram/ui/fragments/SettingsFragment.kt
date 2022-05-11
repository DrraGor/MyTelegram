package ru.dragor.mytelegram.ui.fragments


import android.os.Bundle
import android.view.*
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import ru.dragor.mytelegram.R
import ru.dragor.mytelegram.activities.RegisterActivity
import ru.dragor.mytelegram.databinding.FragmentSetingsBinding
import ru.dragor.mytelegram.utilits.*


class SettingsFragment : BaseFragment(R.layout.fragment_setings) {
    private lateinit var binding: FragmentSetingsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSetingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {

        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        binding.settingsBio.text = USER.bio
        binding.settingsFullName.text = USER.fullname
        binding.settingsPhoneNumber.text = USER.phone
        binding.settingsStatus.text = USER.state
        binding.settingsUsername.text = USER.username
        binding.settingsUserPhoto.downloadAndSetImage(USER.photoUrl)
        binding.settingsBtnChangeUsername.setOnClickListener {
            replaceFragment(ChangeUsernameFragment())
        }
        binding.settingsBtnChangeBio.setOnClickListener {
            replaceFragment(ChangeBioFragment())
        }
        binding.settingsChangePhoto.setOnClickListener {
            changePhotoUser()
        }
    }

    val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uri = result.uriContent
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            if (uri != null)
                putImageToStorage(uri, path) {
                    getUrlFromStorage(path) {
                        putUrlToDatabase(it) {
                            binding.settingsUserPhoto.downloadAndSetImage(it)
                            showToast(getString(R.string.toast_data_update))
                            USER.photoUrl = it
                            APP_ACTIVITY.mAppDrawer.updateHeader()
                        }
                    }
                }
        }
    }



    private fun changePhotoUser() {
        startCrop()

    }

    private fun startCrop() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setMaxCropResultSize(600, 600)
                    .setCropShape(CropImageView.CropShape.OVAL)
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (APP_ACTIVITY.replaceActivity(RegisterActivity()))
            }
            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}