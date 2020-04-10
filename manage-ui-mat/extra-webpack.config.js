const webpack = require('webpack');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const sass = require('sass');
const utils = require('./utils.js');
module.exports = [{
  entry: {
    "deeppurple-amber": './src/main/webapp/assets/custom-themes/deeppurple-amber.scss',
    "indigo-pink": './src/main/webapp/assets/custom-themes/indigo-pink.scss',
    "pink-bluegrey": './src/main/webapp/assets/custom-themes/pink-bluegrey.scss',
    "purple-green": './src/main/webapp/assets/custom-themes/purple-green.scss',
  },
  output: {
    path: utils.root('target/classes/static/'),
    filename: 'app/[name].bundle.css',
    chunkFilename: 'app/[id].chunk.css'
  },
  module: {
    rules: [ {
      test:  /\.scss$/,
      use: [
        MiniCssExtractPlugin.loader,
        'css-loader',
        'postcss-loader',
        {
          loader: 'sass-loader',
          options: { implementation: sass }
        }
      ],
      exclude: /(vendor\.scss|styles\.scss)/
    }
    ]
  },

  plugins: [
    new MiniCssExtractPlugin({
      // Options similar to the same options in webpackOptions.output
      // both options are optional
      filename: "[name].css",
      chunkFilename: "[id].css",
      skipCodeGeneration: true
      // chunkFilename: "[name].css"
    })
  ]
}];
