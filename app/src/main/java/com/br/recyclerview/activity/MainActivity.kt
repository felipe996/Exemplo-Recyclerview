package com.br.recyclerview.activity


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem

import com.br.recyclerview.R
import com.br.recyclerview.adapter.ProdutoAdapter
import com.br.recyclerview.model.Produto
nimport com.br.recyclerview.extensions.setupToolbar
import com.br.recyclerview.extensions.stringMonetarioToDouble
import com.br.recyclerview.util.MascaraMonetaria
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add.view.*
import org.jetbrains.anko.toast
import java.text.DecimalFormat

class MainActivity : BaseActivity() {
    var df = DecimalFormat("#,###,##0.00")
    var alerta: AlertDialog? = null
    var produtos = ArrayList<Produto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Seta Toolbar
        setupToolbar(R.id.toolbar, "Exemplo Recyclerview", false)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.add) {
            val builder = AlertDialog.Builder(context)
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layout = layoutInflater.inflate(R.layout.dialog_add, null)

            with(layout){
                edt_valor.addTextChangedListener(MascaraMonetaria().mask(layout.edt_valor))
                bnt_gravar.setOnClickListener {
                    var produto = Produto()
                    if (edt_descricao.text.toString().isEmpty() && edt_valor.text.toString().isEmpty()){
                        toast("Descrição e Valor são campos obrigatorios")
                    }else{

                        produto.descricao = edt_descricao.text.toString()
                        //Transforma String em Float
                        produto.valor = stringMonetarioToDouble(edt_valor.text.toString())
                        produtos.add(produto)
                        rv_produtos.layoutManager = LinearLayoutManager(context)
                        rv_produtos.setHasFixedSize(true)
                        rv_produtos.adapter = ProdutoAdapter(df,produtos!!,{
                            item: Produto ->
                            toast("${item.descricao} Clicado")

                        })
                        alerta!!.dismiss()
                    }

                }

            }

            builder.setView(layout)
            alerta = builder.create()
            alerta!!.show()



            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
