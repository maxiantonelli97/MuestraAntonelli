package com.example.muestraantonelli.ui.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muestraantonelli.R
import com.example.muestraantonelli.databinding.FragmentSignupBinding
import com.example.muestraantonelli.entity.UsersModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()
    private val permisoCamera = android.Manifest.permission.CAMERA
    private val permisoReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE
    private val permisoWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    val REQUEST_IMAGE_CAPTURE = 1
    private var image: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivSelfie.setOnClickListener{
            checkPermission()
        }

        binding.btSignUp.setOnClickListener{
            val users = UsersModel(0,binding.etNombre.text.toString(), binding.etEmail.text.toString(), binding.etPhone.text.toString(), true)
            if(validarCampos()){
                val aux = viewModel.registrarUsuario(requireContext(), users)
                mostrarSnackBar(requireView(), aux)
                findNavController().navigate(R.id.nav_users)
            }
        }

        binding.etNombre.addTextChangedListener(){
            if(!it.isNullOrBlank()){
                binding.tiNombre.error = null
            }
        }
        binding.etEmail.addTextChangedListener(){
            if(!it.isNullOrBlank()){
                binding.tiEmail.error = null
            }
        }
        binding.etPhone.addTextChangedListener(){
            if(!it.isNullOrBlank()){
                binding.tiPhone.error = null
            }
        }
    }

    private fun mostrarSnackBar(view: View, correcto : Boolean){
        val auxTxt : Int
        val auxColor : Int
        if(correcto){
            auxTxt = R.string.usuariocreado
            auxColor = resources.getColor(R.color.teal_200)
        } else {
            auxTxt = R.string.errorcreandousuario
            auxColor = resources.getColor(R.color.black)
        }
        Snackbar.make(view, auxTxt, Snackbar.LENGTH_LONG).setBackgroundTint(auxColor).show()
    }

    fun validarCampos() : Boolean{
        var correct = true
        if(binding.etNombre.text.isNullOrBlank()){
            correct = false
            binding.tiNombre.error = "Campo obligatorio"
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()){
            correct = false
            binding.tiEmail.error = "Mail invalido"
        }
        if(binding.etEmail.text.isNullOrBlank()){
            correct = false
            binding.tiEmail.error = "Campo obligatorio"
        }
        if(binding.etPhone.text.isNullOrBlank()){
            correct = false
            binding.tiPhone.error = "Campo obligatorio"
        }
        return correct
    }

    private fun checkPermission() {

        val deboProveerContexto = ContextCompat.checkSelfPermission(requireActivity(), permisoCamera) == PackageManager.PERMISSION_GRANTED
        if (deboProveerContexto) {
            solicitarPermisos()
        } else {
            solicitarPermisos()
        }
    }

    private fun solicitarPermisos(){
        requestPermissions(arrayOf(permisoCamera, permisoReadStorage, permisoWriteStorage), REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            val todosLosPermisosConcedidos =
                grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (grantResults.isNotEmpty() && todosLosPermisosConcedidos) {
                intentPhoto()
            } else {
                mostrarToast()
            }
        }
    }

    private fun intentPhoto(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePicture ->
            takePicture.resolveActivity(activity?.packageManager!!).also {
                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            data?.extras?.let { bundle ->
                image = bundle.get("data") as Bitmap
                binding.ivSelfie.setImageBitmap(image)
            }
        }
    }

    private fun mostrarToast(){
        Toast.makeText(context, R.string.toastPermisos, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}