package ml.combust.mleap.runtime.bundle.ops.feature

import ml.combust.bundle.dsl._
import ml.combust.bundle.op.{OpModel, OpNode}
import ml.combust.bundle.serializer.BundleContext
import ml.combust.mleap.core.feature.NGramModel
import ml.combust.mleap.runtime.transformer.feature.NGram

/**
  * Created by mikhail on 10/16/16.
  */
object NGramOp extends OpNode[NGram, NGramModel]{
  override val Model: OpModel[NGramModel] = new OpModel[NGramModel] {
    override def opName: String = Bundle.BuiltinOps.feature.ngram

    override def store(context: BundleContext, model: Model, obj: NGramModel): Model = {
      model.withAttr(Attribute("n", Value.long(obj.n)))
    }

    override def load(context: BundleContext, model: Model): NGramModel = {
      NGramModel(n = model.value("n").getLong.toInt)
    }
  }

  override def name(node: NGram): String = node.uid

  override def model(node: NGram): NGramModel = node.model

  override def load(context: BundleContext, node: Node, model: NGramModel): NGram = {
    NGram(inputCol = node.shape.standardInput.name,
      outputCol = node.shape.standardOutput.name,
      model = model)
  }

  override def shape(node: NGram): Shape = Shape().withStandardIO(node.inputCol, node.outputCol)
}