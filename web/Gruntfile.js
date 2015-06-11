// Generated on 2015-06-10 using generator-angular-fullstack 2.0.13
'use strict';

module.exports = function (grunt) {

  // Load grunt tasks automatically, when needed
  require('jit-grunt')(grunt, {
    connect: 'grunt-contrib-connect',
    useminPrepare: 'grunt-usemin',
    ngtemplates: 'grunt-angular-templates',
    protractor: 'grunt-protractor-runner',
    injector: 'grunt-asset-injector',
  });

  // Time how long tasks take. Can help when optimizing build times
  require('time-grunt')(grunt);

  // Define the configuration for all the tasks
  grunt.initConfig({

    // Project settings
    pkg: grunt.file.readJSON('package.json'),
    config: {
      // configurable paths
      client: require('./bower.json').appPath || 'src/main/webapp',
      dist: 'build/webapp',
      tmp: 'build/.tmp'
    },
    connect: {
      server: {
        options: {
          port: 9000,
          hostname: 'localhost',
          middleware: function(connect, options) {
            return [
              require('connect-livereload')(),
              mountFolder(connect, 'client'),
              require('grunt-connect-proxy/lib/utils').proxyRequest
            ];
          },
          proxies: [{
            context: '/',
            host: 'localhost',
            port: 9088
          }]
        }
      }
    },
    open: {
      server: {
        url: 'http://localhost:9000'
      }
    },
    watch: {
      injectJS: {
        files: [
          '<%= config.client %>/{app,components}/**/*.js',
          '!<%= config.client %>/{app,components}/**/*.spec.js',
          '!<%= config.client %>/{app,components}/**/*.mock.js',
          '!<%= config.client %>/app/app.js'],
        tasks: ['injector:scripts']
      },
      injectCss: {
        files: [
          '<%= config.client %>/{app,components}/**/*.css'
        ],
        tasks: ['injector:css']
      },
      jsTest: {
        files: [
          '<%= config.client %>/{app,components}/**/*.spec.js',
          '<%= config.client %>/{app,components}/**/*.mock.js'
        ],
        tasks: ['newer:jshint:all', 'karma']
      },
      gruntfile: {
        files: ['Gruntfile.js']
      },
      livereload: {
        files: [
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.css',
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.html',
          '{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.js',
          '!{<%= config.tmp %>,<%= config.client %>}{app,components}/**/*.spec.js',
          '!{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.mock.js',
          '<%= config.client %>/assets/images/{,*//*}*.{png,jpg,jpeg,gif,webp,svg}'
        ],
        options: {
          livereload: true
        }
      },
      express: {
        files: [
          'server/**/*.{js,json}'
        ],
        tasks: ['express:dev', 'wait'],
        options: {
          livereload: true,
          nospawn: true //Without this option specified express won't be reloaded
        }
      }
    },

    // Make sure code styles are up to par and there are no obvious mistakes
    jshint: {
      options: {
        jshintrc: '<%= config.client %>/.jshintrc',
        reporter: require('jshint-stylish')
      },
      all: [
        '<%= config.client %>/{app,components}/**/*.js',
        '!<%= config.client %>/{app,components}/**/*.spec.js',
        '!<%= config.client %>/{app,components}/**/*.mock.js'
      ],
      test: {
        src: [
          '<%= config.client %>/{app,components}/**/*.spec.js',
          '<%= config.client %>/{app,components}/**/*.mock.js'
        ]
      }
    },

    // Add vendor prefixed styles
    autoprefixer: {
      options: {
        browsers: ['last 1 version']
      },
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.tmp %>/',
          src: '{,*/}*.css',
          dest: '<%= config.tmp %>/'
        }]
      }
    },

    // Automatically inject Bower components into the app
    wiredep: {
      target: {
        src: '<%= config.client %>/index.html',
        ignorePath: '<%= config.client %>/',
        exclude: [/bootstrap-sass-official/, /bootstrap.js/, '/json3/', '/es5-shim/']
      }
    },

    // Renames files for browser caching purposes
    rev: {
      dist: {
        files: {
          src: [
            '<%= config.dist %>/public/{,*/}*.js',
            '<%= config.dist %>/public/{,*/}*.css',
            '<%= config.dist %>/public/assets/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
            '<%= config.dist %>/public/assets/fonts/*'
          ]
        }
      }
    },

    // Reads HTML for usemin blocks to enable smart builds that automatically
    // concat, minify and revision files. Creates configurations in memory so
    // additional tasks can operate on them
    useminPrepare: {
      html: ['<%= config.client %>/index.html'],
      options: {
        dest: '<%= config.dist %>/public',
        staging: '<%= config.tmp %>'
      }
    },

    // Performs rewrites based on rev and the useminPrepare configuration
    usemin: {
      html: ['<%= config.dist %>/public/{,*/}*.html'],
      css: ['<%= config.dist %>/public/{,*/}*.css'],
      js: ['<%= config.dist %>/public/{,*/}*.js'],
      options: {
        assetsDirs: [
          '<%= config.dist %>/public',
          '<%= config.dist %>/public/assets/images'
        ],
        // This is so we update image references in our ng-templates
        patterns: {
          js: [
            [/(assets\/images\/.*?\.(?:gif|jpeg|jpg|png|webp|svg))/gm, 'Update the JS to reference our revved images']
          ]
        }
      }
    },

    // Allow the use of non-minsafe AngularJS files. Automatically makes it
    // minsafe compatible so Uglify does not destroy the ng references
    ngAnnotate: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= config.tmp %>/concat',
          src: '*/**.js',
          dest: '<%= config.tmp %>/concat'
        }]
      }
    },

    // Package all the html partials into a single javascript payload
    ngtemplates: {
      options: {
        // This should be the name of your apps angular module
        module: 'privatlakareApp',
        htmlmin: {
          collapseBooleanAttributes: true,
          collapseWhitespace: true,
          removeAttributeQuotes: true,
          removeEmptyAttributes: true,
          removeRedundantAttributes: true,
          removeScriptTypeAttributes: true,
          removeStyleLinkTypeAttributes: true
        },
        usemin: 'app/app.js'
      },
      main: {
        cwd: '<%= config.client %>',
        src: ['{app,components}/**/*.html'],
        dest: '<%= config.tmp %>/templates.js'
      },
      tmp: {
        cwd: '<%= config.tmp %>',
        src: ['{app,components}/**/*.html'],
        dest: '<%= config.tmp %>/tmp-templates.js'
      }
    },

    // Copies remaining files to places other tasks can use
    copy: {
      dist: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= config.client %>',
          dest: '<%= config.dist %>/public',
          src: [
            '*.{ico,png,txt}',
            '.htaccess',
            'bower_components/**/*',
            'assets/images/{,*/}*.{webp}',
            'assets/fonts/**/*',
            'index.html'
          ]
        }, {
          expand: true,
          cwd: '<%= config.tmp %>/images',
          dest: '<%= config.dist %>/public/assets/images',
          src: ['generated/*']
        }, {
          expand: true,
          dest: '<%= config.dist %>',
          src: [
            'package.json',
            'server/**/*'
          ]
        }]
      },
      styles: {
        expand: true,
        cwd: '<%= config.client %>',
        dest: '<%= config.tmp %>/',
        src: ['{app,components}/**/*.css']
      }
    },

    // Test settings
    karma: {
      unit: {
        configFile: 'karma.conf.js',
        singleRun: true
      }
    },

    protractor: {
      options: {
        configFile: 'protractor.conf.js'
      },
      chrome: {
        options: {
          args: {
            browser: 'chrome'
          }
        }
      }
    },

    injector: {
      options: {

      },
      // Inject application script files into index.html (doesn't include bower)
      scripts: {
        options: {
          transform: function(filePath) {
            filePath = filePath.replace('/src/main/webapp/', '');
            filePath = filePath.replace('/<%= config.tmp %>/', '');
            return '<script src="' + filePath + '"></script>';
          },
          starttag: '<!-- injector:js -->',
          endtag: '<!-- endinjector -->'
        },
        files: {
          '<%= config.client %>/index.html': [
              ['{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.js',
               '!{<%= config.tmp %>,<%= config.client %>}/app/app.js',
               '!{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.spec.js',
               '!{<%= config.tmp %>,<%= config.client %>}/{app,components}/**/*.mock.js']
            ]
        }
      },

      // Inject component css into index.html
      css: {
        options: {
          transform: function(filePath) {
            filePath = filePath.replace('/src/main/webapp/', '');
            filePath = filePath.replace('/<%= config.tmp %>/', '');
            return '<link rel="stylesheet" href="' + filePath + '">';
          },
          starttag: '<!-- injector:css -->',
          endtag: '<!-- endinjector -->'
        },
        files: {
          '<%= config.client %>/index.html': [
            '<%= config.client %>/{app,components}/**/*.css'
          ]
        }
      }
    },
  });

  // Used for delaying livereload until after server has restarted
  grunt.registerTask('wait', function () {
    grunt.log.ok('Waiting for server reload...');

    var done = this.async();

    setTimeout(function () {
      grunt.log.writeln('Done waiting!');
      done();
    }, 1500);
  });

  grunt.registerTask('connect-keepalive', 'Keep grunt running', function() {
    this.async();
  });

  grunt.registerTask('serve', function (target) {
    if (target === 'dist') {
      return grunt.task.run(['build', 'express:prod', 'wait', 'open', 'connect-keepalive']);
    }

    if (target === 'debug') {
      return grunt.task.run([
        'concurrent:server',
        'injector',
        'wiredep',
        'autoprefixer',
        'concurrent:debug'
      ]);
    }

    grunt.task.run([
      'concurrent:server',
      'injector',
      'wiredep',
      'autoprefixer',
      'express:dev',
      'wait',
      'open',
      'watch'
    ]);
  });

  grunt.registerTask('server', function () {
    grunt.log.warn('The `server` task has been deprecated. Use `grunt serve` to start a server.');
    grunt.task.run(['serve']);
  });

  grunt.registerTask('test', function(target) {
    if (target === 'client') {
      return grunt.task.run([
        'injector',
        'autoprefixer',
        'karma'
      ]);
    }

    else if (target === 'e2e') {
      return grunt.task.run([
        'injector',
        'wiredep',
        'autoprefixer',
        'express:dev',
        'protractor'
      ]);
    }

    else grunt.task.run([
      'test:client'
    ]);
  });

  grunt.registerTask('build', [
    'injector',
    'wiredep',
    'useminPrepare',
    'autoprefixer',
    'ngtemplates',
    'concat',
    'ngAnnotate',
    'copy:dist',
    'cssmin',
    'uglify',
    'rev',
    'usemin'
  ]);

  grunt.registerTask('default', [
    'newer:jshint',
    'test',
    'build'
  ]);
};
