package com.example.muestraantonelli.ui.users

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.muestraantonelli.R
import com.example.muestraantonelli.databinding.FragmentUsersBinding
import com.example.muestraantonelli.entity.UsersModel
import com.example.muestraantonelli.ui.users.utils.LongClickListener
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val viewModel: UsersViewModel by viewModels()
    private var usersAdapter: UsersAdapter? = null
    private val binding get() = _binding!!
    val users: ArrayList<UsersModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.bringToFront()

        Picasso.with(this.context).load("https://images.pexels.com/photos/5412101/pexels-photo-5412101.jpeg")
            .networkPolicy(NetworkPolicy.NO_STORE, NetworkPolicy.NO_CACHE).fit().centerCrop()
            .error(R.drawable.ic_baseline_clear_24).into(binding.ivEmpresa)

        viewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                mostrarSnackBar(requireView(), R.string.errorAPI, resources.getColor(R.color.white))
            } else {
                viewModel.users.value?.let { it1 -> users.addAll(it1) }
            }
            users.addAll(viewModel.obtenerUsuariosDB(requireContext()))
            setAdapter()
            binding.progressBar.visibility = View.GONE
        }


        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.nav_signup)
        }
        return binding.root

    }

    private fun setAdapter() {
        usersAdapter = UsersAdapter(users, object : LongClickListener {
            override fun longClick(v: View, index: Int, user: UsersModel) {
                dialogEliminarItem(index, user)
            }
        })
        binding.rvUsers.adapter = usersAdapter
    }

    fun dialogEliminarItem(index: Int, user: UsersModel) {
        AlertDialog.Builder(context)
            .setMessage(R.string.confirma)
            .setPositiveButton(
                R.string.eliminar
            ) { _, _ ->
                usersAdapter?.eliminarItem(index)
                if (user.local) {
                    viewModel.eliminarItemDB(user, requireContext())
                }
                mostrarSnackBar(
                    requireView(),
                    R.string.usuarioeliminado,
                    resources.getColor(R.color.purple_700)
                )
            }
            .setNegativeButton(
                R.string.cancelar
            ) { _, _ ->
            }
            .show()
    }

    private fun mostrarSnackBar(view: View, texto: Int, color: Int) {
        Snackbar.make(view, texto, Snackbar.LENGTH_LONG).setBackgroundTint(color).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
