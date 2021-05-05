module.exports = {
    devServer: {
        port: 8080,
        proxy: {
            'api/': {
                target: 'http://localhost:8080',
                ws: true
            }
        }
    },
    runtimeCompiler: true
}
